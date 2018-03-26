package com.douyu.supermap.shakingmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "visitor/hello";
    }

}
