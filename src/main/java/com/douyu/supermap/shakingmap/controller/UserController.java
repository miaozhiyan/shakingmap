package com.douyu.supermap.shakingmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "user/hello";
    }
}
