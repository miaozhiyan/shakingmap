package com.douyu.supermap.shakingmap.config;

import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthProvider implements AuthenticationProvider{
    @Autowired
    private IUserService userService;

    private final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();
        User user = userService.findUserByAccount(userName);
        if (null == user){
            throw new AuthenticationCredentialsNotFoundException("Auth info not found!");
        }
        if (this.passwordEncoder.isPasswordValid(user.getPwd(),inputPassword,user.getId())){
            return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        }
        throw new BadCredentialsException("Auth error,pwd not correct!");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
