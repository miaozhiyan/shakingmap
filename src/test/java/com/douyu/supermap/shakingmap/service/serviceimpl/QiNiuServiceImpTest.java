package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import com.douyu.supermap.shakingmap.service.interfaces.IQiNiuService;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class QiNiuServiceImpTest  extends ShakingmapApplicationTests {
    @Autowired
    private IQiNiuService qiNiuService;

    @Test
    public void uploadFile() throws Exception {
        File file = new File("C:\\Users\\lenovo\\Desktop\\启动redis.jpg");
        Assert.assertTrue(file.exists());
        Response response = qiNiuService.uploadFile(file);
        String str = response.bodyString();
        Assert.assertTrue(response.isOK());
    }

    @Test
    public void uploadFile1() throws Exception {
    }

    @Test
    public void delete() throws Exception {
        String key = "FgL-DXgVOBL5kZWbs5C1wbKE4x22";
        Response response = qiNiuService.delete(key);
        Assert.assertTrue(response.isOK());
    }

    @Test
    public void afterPropertiesSet() throws Exception {
    }

}