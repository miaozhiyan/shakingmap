package com.douyu.supermap.shakingmap.config.springsecurity;

import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.utils.LoginUserUtils;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthFilter extends UsernamePasswordAuthenticationFilter{
    @Autowired
    private IUserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String name = obtainUsername(request);
        if (!StringUtils.isNullOrEmpty(name)){
            request.setAttribute("username",name);
            return super.attemptAuthentication(request,response);
        }
        String telephone = request.getParameter("telephone");
        if (!LoginUserUtils.checkTelephone(telephone)){
            throw new BadCredentialsException("电话号码错误:["+telephone+"]");
        }

        User user = userService.addUserByTel(telephone);
        String inputSmsCode = request.getParameter("smsCode");
        String sessionCode = null;//TODO 短信验证码
        if (!Objects.equals(inputSmsCode,sessionCode)){//不等
            throw new BadCredentialsException("验证码错误");
        }

        if (user==null){//若用户第一次用手机登录，需要帮他注册一下
            user = userService.addUserByTel(telephone);
        }
        return new UsernamePasswordAuthenticationToken(user,null,
                user.getAuthorities());

//        return super.attemptAuthentication(request, response);
    }
}
