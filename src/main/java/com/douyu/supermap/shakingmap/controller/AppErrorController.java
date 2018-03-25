package com.douyu.supermap.shakingmap.controller;

import com.douyu.supermap.shakingmap.common.vo.ResultVo;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * web 错误全局配置
 */
@Controller
public class AppErrorController implements ErrorController{
    private static final String ERROR_PATH = "/error";

    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    public AppErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes=errorAttributes;
    }

    /**
     * web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH,produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response){
        int status = response.getStatus();
        switch (status){
            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
            default:
                return "index";
        }
    }

    /**
     * 除开web页面以外的错误处理，如json,xml等
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResultVo errorApiHandler(HttpServletRequest request){
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String,Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes,false);
        int status = getStatus(request);
        return  new ResultVo(status,String.valueOf(attr.getOrDefault("message","error")),null);
    }

    private int getStatus(HttpServletRequest request){
        //固定的name名javax.servlet.error.status_code
        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (status !=null){
            return status;
        }
        return 500;
    }
}
