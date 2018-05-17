package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.utils.FileUploadUtils;
import com.douyu.supermap.shakingmap.common.vo.inner.ContentTemplate;
import com.douyu.supermap.shakingmap.common.vo.req.AddNewContentReq;
import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.config.redis.RedisManager;
import com.douyu.supermap.shakingmap.service.interfaces.IUserService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final long MAX_CONTENT_FILE_SIZE = 8* FileUtils.ONE_MB;

    private List<ContentTemplate> templates = new ArrayList<>();

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisManager redisManager;

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
        if (!FileUploadUtils.isLegalFmt(file.getOriginalFilename(),new String[]{"mp4","avi","jpg","jpeg","bmp","png"})){
            return ResultVo.asError("上传文件类型不匹配");
        }

        try {
            userService.addContent(file,req,request);
        }catch (Exception e){
            logger.error("上传内容失败:",e);
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

    @RequestMapping("/register")
    public String registerUser(String account,String pwd){
        ResultVo resultVo = userService.addUser(account,pwd);
        return "user/success_register";
    }

    @GetMapping("/center")
    public String centerPage(){
        return "user/center";
    }

    @GetMapping("/showMagicPosition")
    @ResponseBody
    public ResultVo showMagicPosition(){
        templates.clear();

        ContentTemplate template = new ContentTemplate();
        template.setId(1L);
        template.setLocationRegion("西陵区");
        template.setLocationCity("宜昌");
        template.setLocationCountry("中国");
        template.setNickname("测试的昵称");
        template.setContentNote("测试的地点");
        template.setBaiduMapLongtitue(111.31018);
        template.setBaiduMapLatitude(30.73268);
        String favorCount = redisManager.get("contentFavorCount:"+template.getId());
        if (favorCount != null){
            template.setFavoriteCount(Integer.parseInt(favorCount));
        }
        templates.add(template);


        ContentTemplate template2 = new ContentTemplate();
        template2.setId(2L);
        template2.setLocationRegion("江夏区");
        template2.setLocationCity("武汉");
        template2.setLocationCountry("中国");
        template2.setNickname("缪添加");
        template2.setContentNote("华夏学院上面");
        template2.setBaiduMapLongtitue(114.418145);
        template2.setBaiduMapLatitude(30.475546);
        template2.setContentUrl("p6a8636wy.bkt.clouddn.com/Fk3xbwqXpsZ1-h3uucTus-VJjyuo");
        String favorCount2 = redisManager.get("contentFavorCount:"+template2.getId());
        if (favorCount2 != null){
            template2.setFavoriteCount(Integer.parseInt(favorCount2));
        }
        templates.add(template2);

        return ResultVo.asSuccess(templates);
    }


    @PostMapping("/markFavor")
    @ResponseBody
    public ResultVo markFavor(String latADlng){

        String [] data = latADlng.split(",");
        String lat = data[0];
        String lng = data[1];
        templates.forEach(
                contentTemplate->{
                    boolean cor =
                    (
                            Math.abs(contentTemplate.getBaiduMapLatitude() - Double.parseDouble(lat))<0.001 &&
                            Math.abs(contentTemplate.getBaiduMapLongtitue() - Double.parseDouble(lng))<0.001
                    );

                    if (cor){
                        redisManager.incre("contentFavorCount:"+contentTemplate.getId());
                    }
                }
        );

        return showMagicPosition();
    }


    @PostMapping(value = "upload/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultVo uploadPhoto(@RequestParam("file")MultipartFile file,Long uid){
        if (file==null || file.isEmpty()){
            return ResultVo.asError("上传文件为空!");
        }

        try {
            userService.uploadPhoto(file,uid);
        }catch (Exception e){
            logger.error("上传头像失败:",e);
            return ResultVo.asError("上传图片异常");
        }
        return ResultVo.asSuccess();
    }
}
