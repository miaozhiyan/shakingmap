package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping("getLocaltion")
    @ResponseBody
    public ResultVo locationInfo(){
        return null;
    }

    @GetMapping("/mainMap")
    public String mainMap(){
        return "map";
    }


}
