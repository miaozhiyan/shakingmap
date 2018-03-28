package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.vo.ResultVo;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "user/hello";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }
    @GetMapping("/center")
    public String centerPage(){
        return "user/center";
    }

    @PostMapping(value = "user/upload/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVo uploadPhoto(@RequestParam("file")MultipartFile file){
        if (file==null || file.isEmpty()){
            return ResultVo.asError("上传文件为空!");
        }

        try {
            userService.uploadPhoto(file);
        }catch (Exception e){
            return ResultVo.asError("上传图片异常");
        }
        return ResultVo.asSuccess();
    }
}
