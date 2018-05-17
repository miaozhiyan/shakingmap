package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.service.serviceimpl.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @RequestMapping("/register")
    public String hello(){
        return "user/register";
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


    @RequestMapping("/signInCheckAcc")
    @ResponseBody
    public ResultVo signInCheckAcc(String account){
        return visitorService.signInCheckAcc(account);
    }

}
