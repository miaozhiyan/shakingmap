package com.douyu.supermap.shakingmap.common.utils;

import com.douyu.supermap.shakingmap.common.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserUtils {
    public static User load(){
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal != null && principal instanceof User){
            return (User)principal;
        }
        return null;
    }

    public static Long getLoginUid(){
        User user = load();
        if (user == null){
            return -1L;
        }
        return user.getId();
    }
}
