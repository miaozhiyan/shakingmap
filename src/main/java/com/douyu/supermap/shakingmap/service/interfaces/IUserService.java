package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    User findUserByAccount(String account);

    ResultVo uploadPhoto(MultipartFile file);
}
