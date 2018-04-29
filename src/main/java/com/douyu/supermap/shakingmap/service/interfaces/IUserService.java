package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.vo.req.AddNewContentReq;
import com.douyu.supermap.shakingmap.common.vo.req.QueryUserReq;
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IUserService {
    User findUserByAccount(String account);

    ResultVo uploadPhoto(MultipartFile file,Long uid) throws IOException;

    void addContent(MultipartFile file, AddNewContentReq req, HttpServletRequest request) throws IOException;

    List<User> queryUser(QueryUserReq queryUserReq);

    User findUserByTel(String tel);

    /**
     * 通过手机号注册用户
     * @param tel
     * @return
     */
    User addUserByTel(String tel);
}
