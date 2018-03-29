package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.utils.FileUploadUtils;
import com.douyu.supermap.shakingmap.common.vo.req.AddNewContentReq;
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final long MAX_CONTENT_FILE_SIZE = 8* FileUtils.ONE_MB;

    @Autowired
    private IUserService userService;

    @PostMapping("/content/add")
    @ResponseBody
    public ResultVo addContent(@NotNull MultipartFile file, @Valid AddNewContentReq req,
                               HttpServletRequest request, BindingResult result){
        if (result.hasErrors()){
            return ResultVo.asError(result.getAllErrors().get(0).getDefaultMessage());
        }
        if (file==null||file.getSize() == 0L || file.getSize()>MAX_CONTENT_FILE_SIZE){
            return ResultVo.asError("文件大小不能超过"+ FileUploadUtils.transformLongToFileSize(MAX_CONTENT_FILE_SIZE));
        }
        if (FileUploadUtils.isLegalFmt(file.getOriginalFilename(),new String[]{"mp4","avi"})){
            return ResultVo.asError("上传文件类型不匹配");
        }

        try {
            userService.addContent(file,req,request);
        }catch (Exception e){
            return ResultVo.asError("添加内容失败");
        }
        return ResultVo.asSuccess();
    }



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
