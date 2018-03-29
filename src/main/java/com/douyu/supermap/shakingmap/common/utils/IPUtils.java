package com.douyu.supermap.shakingmap.common.utils;

import com.douyu.supermap.shakingmap.common.vo.inner.IpInfoVo;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    private static final String taobaoIpsearch="http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static RestTemplate restTemplate=new RestTemplate();

    public static IpInfoVo getIpInfo(String ip){
        String res = restTemplate.getForObject(taobaoIpsearch+ip,String.class);
        Gson gson = new Gson();
        IpInfoVo ipInfoVo = gson.fromJson(res,IpInfoVo.class);
        return ipInfoVo;
    }

    public static String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
