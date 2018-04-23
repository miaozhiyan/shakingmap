package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;
import com.douyu.supermap.shakingmap.service.interfaces.IVisitorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class VisitorService implements IVisitorService{
    private static final String BAIDU_MAP_GEOCONV_API = "http://api.map.baidu.com/geocoder/v2/?";

    @Value("${baidu.serverKey}")
    private String baiduServerKey;


    public ResultVo locationInfo(String address,String city) throws IOException {
        address = URLEncoder.encode(address,"UTF-8");
        city = URLEncoder.encode(city,"UTF-8");
        StringBuilder sb = new StringBuilder(BAIDU_MAP_GEOCONV_API);
        sb.append("address=").append(address).append("&city=").append(city)
                .append("&ak=").append(baiduServerKey);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(sb.toString());

        HttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
            return ResultVo.asError("获取百度地图经纬度api失败");
        }
        String res = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(res);

        int status = jsonNode.get("status").asInt();
        if (status!=0){
            return ResultVo.asError("获取百度地图经纬度api失败");
        }

        JsonNode jsonLocation = jsonNode.get("result").get("location");
        Double lng = jsonLocation.get("lng").asDouble();
        Double lat = jsonLocation.get("lat").asDouble();

        return ResultVo.asSuccess("lng="+lng+";lat="+lat);
    }
}
