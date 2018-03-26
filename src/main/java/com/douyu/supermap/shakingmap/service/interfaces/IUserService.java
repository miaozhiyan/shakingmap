package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.entity.User;

public interface IUserService {
    User findUserByAccount(String account);
}
