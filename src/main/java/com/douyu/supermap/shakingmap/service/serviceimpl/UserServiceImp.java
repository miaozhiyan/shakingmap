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
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.dao.ContentRepository;
import com.douyu.supermap.shakingmap.dao.RoleRepository;
import com.douyu.supermap.shakingmap.dao.UserRepository;
import com.douyu.supermap.shakingmap.service.interfaces.IQiNiuService;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
}
