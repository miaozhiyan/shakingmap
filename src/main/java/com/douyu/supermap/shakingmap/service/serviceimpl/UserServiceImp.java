package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.supermap.shakingmap.common.entity.Content;
import com.douyu.supermap.shakingmap.common.entity.Role;
import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.utils.FileUploadUtils;
import com.douyu.supermap.shakingmap.common.utils.IPUtils;
import com.douyu.supermap.shakingmap.common.vo.inner.IpInfoData;
import com.douyu.supermap.shakingmap.common.vo.inner.IpInfoVo;
import com.douyu.supermap.shakingmap.common.vo.inner.QiNiuResBodyVo;
import com.douyu.supermap.shakingmap.common.vo.req.AddNewContentReq;
import com.douyu.supermap.shakingmap.common.vo.req.QueryUserReq;
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.dao.ContentRepository;
import com.douyu.supermap.shakingmap.dao.RoleRepository;
import com.douyu.supermap.shakingmap.dao.UserRepository;
import com.douyu.supermap.shakingmap.service.interfaces.IQiNiuService;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements IUserService{
    @Autowired
    private IQiNiuService qiNiuService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private Gson gson;

    @Value("${qiniu.cdn.prefix}")
    private String QINIU_URL;

    private static final File ROOT_TEMP_WORK_SPACE =new File(SystemUtils.getJavaIoTmpDir(),"magic_map_temp_work_space");

    static {
        FileUploadUtils.mkDir(ROOT_TEMP_WORK_SPACE);
    }

    @Override
    public User findUserByAccount(String account) {
        User user = userRepository.findByAccount(account);
        if (user == null){
            return null;
        }
        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
        if (null == roles || roles.isEmpty()){
            throw new DisabledException("未找到任何一个权限，userId="+user.getId());
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
        );
        user.setAuthorityList(authorities);

        return user;
    }

    @Override
    @Transactional
    public ResultVo uploadPhoto(MultipartFile file,Long uid) throws IOException {
        File temp = new File(ROOT_TEMP_WORK_SPACE,System.currentTimeMillis()+"uid"+uid);
        try {
            file.transferTo(temp);
            Response response = qiNiuService.uploadFile(temp);
            QiNiuResBodyVo resBody = gson.fromJson(response.bodyString(), QiNiuResBodyVo.class);
            String avatorUrl = QINIU_URL+"/"+resBody.key;/* rpc调用后赋值 */
            userRepository.updateAvatarByUid(avatorUrl,uid);
        }finally {
            FileUtils.forceDelete(temp);
        }
        return ResultVo.asSuccess();
    }

    @Override
    public void addContent(MultipartFile uploadFile, AddNewContentReq req, HttpServletRequest request) throws IOException {
        File temp = new File(ROOT_TEMP_WORK_SPACE,System.currentTimeMillis()+"uid"+req.getUid());
        try {
            uploadFile.transferTo(temp);
            Response response = qiNiuService.uploadFile(temp);
            Content newContent = new Content();
            BeanUtils.copyProperties(req,newContent);/*直接赋值*/
            newContent.setCreateTime(System.currentTimeMillis());

            QiNiuResBodyVo resBody = gson.fromJson(response.bodyString(), QiNiuResBodyVo.class);
            newContent.setContentUrl(QINIU_URL+"/"+resBody.key);/* rpc调用后赋值 */

            IpInfoVo ipInfoVo = IPUtils.getIpInfo(IPUtils.getRemoteHost(request));
            IpInfoData infoData = ipInfoVo.data;
            StringBuilder location = new StringBuilder();
            location.append(infoData.country).append("--")
                    .append(infoData.region).append("--")
                    .append(infoData.city);
            newContent.setLocation(location.toString());

            contentRepository.save(newContent);
        }finally {
            FileUtils.forceDelete(temp);
        }
    }

    @Override
    public List<User> queryUser(QueryUserReq queryUserReq) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = new PageRequest(queryUserReq.getPageNo()-1,queryUserReq.getPageSize(),sort);

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if (StringUtils.isNotBlank(queryUserReq.getAccount())){
                    predicate = criteriaBuilder.equal(root.get("account"),queryUserReq.getAccount());
                }

                if (StringUtils.isNotBlank(queryUserReq.getNickname())){
                    Predicate p2 = criteriaBuilder.equal(root.get("nickname"),queryUserReq.getNickname());
                    if (predicate == null){
                        predicate = p2;
                    }else{
                        predicate = criteriaBuilder.and(predicate,p2);
                    }
                }

                if (queryUserReq.getExp()!=null){
                    Predicate p3 = criteriaBuilder.ge(root.get("exp"),queryUserReq.getExp());
                    if (predicate == null){
                        predicate = p3;
                    }else{
                        predicate = criteriaBuilder.and(predicate,p3);
                    }
                }

                return predicate;
            }
        };

        Page<User> page = userRepository.findAll(specification,pageRequest);

        return page.getContent();
    }

    @Override
    public User findUserByTel(String tel) {
        User user = userRepository.findUserByTel(tel);
        if (user == null){
            return null;
        }
        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
        if (roles==null || roles.isEmpty()){
            throw new DisabledException("权限非法");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(
                role->authorities.add(
                        new SimpleGrantedAuthority("ROLE_"+role.getRoleName())
                )
        );
        user.setAuthorityList(authorities);
        return user;
    }

    @Override
    @Transactional
    public User addUserByTel(String tel) {
        User user = new User();
        user.setTel(tel);
        user.setNickname(tel.substring(0,3)+"****"+tel.substring(7,tel.length()));
        user.setCreateTime(System.currentTimeMillis());
        userRepository.save(user);
        Role role = new Role();
        role.setRoleName("USER");
        role.setUserId(user.getId());
        roleRepository.save(role);
        user.setAuthorityList(Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER")));
        return user;
    }
}
