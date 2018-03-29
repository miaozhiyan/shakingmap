package com.douyu.supermap.shakingmap.config.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    /**
     * HTTP权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
                Ant风格路径表达式
                ?	匹配任何单字符
                *	匹配0或者任意数量的字符
                **	匹配0或者更多的目录
         */
        http.authorizeRequests()
                .antMatchers("/visitor/**").permitAll() //游客
                .antMatchers("/static/**").permitAll()  //静态资源
                .antMatchers("/user/login").permitAll() //用户登录
                .antMatchers("/admin/login").permitAll() //管理员登陆
//                .antMatchers("/admin/**").hasRole("ADMIN") //管理员
//                .antMatchers("/user/**").hasAnyRole("ADMIN","USER") //用户
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureHandler(loginAuthFailHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
//                .authenticationEntryPoint(entryPoint())
                .accessDeniedPage("/403");
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * 自定义认证策略
     * 注意：
     * @EnableWebSecurity、@EnableGlobalMethodSecurity
     * 这两个注解下只能有一个AuthenticationManagerBuilder注入
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
//        managerBuilder
//                .inMemoryAuthentication()
//                .withUser("mmm")
//                .password("666")
//                .roles("USER")
//                .and();

        managerBuilder.authenticationProvider(authProvider()).eraseCredentials(true);
    }

    @Bean
    public AuthProvider authProvider(){
        return new AuthProvider();
    }

    @Bean
    public LoginUrlEntryPoint entryPoint(){
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginAuthFailHandler loginAuthFailHandler(){
        return new LoginAuthFailHandler(entryPoint());
    }
}
