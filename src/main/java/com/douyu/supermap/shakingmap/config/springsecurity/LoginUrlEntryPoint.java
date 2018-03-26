package com.douyu.supermap.shakingmap.config.springsecurity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于角色的登录入口控制器
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint{

    private PathMatcher pathMatcher = new AntPathMatcher();
    private final Map<String,String> authEntryPointMap;

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        //映射对应关系
        authEntryPointMap = new HashMap<>();
        authEntryPointMap.put("/user/**","/user/login");
        authEntryPointMap.put("/admin/**","/admin/login");

    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     AuthenticationException exception) {
        String uri = request.getRequestURI();
        StringUtils.replace(uri,request.getContextPath(),"");
        for (Map.Entry<String,String> authEnty : this.authEntryPointMap.entrySet()){
            if (this.pathMatcher.match(authEnty.getKey(),uri)){
                return authEnty.getValue();
            }
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
