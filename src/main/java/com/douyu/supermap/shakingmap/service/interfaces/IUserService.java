package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.vo.req.AddNewContentReq;
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface IUserService {
    User findUserByAccount(String account);

    ResultVo uploadPhoto(MultipartFile file);

    void addContent(MultipartFile file, AddNewContentReq req, HttpServletRequest request) throws IOException;
}
