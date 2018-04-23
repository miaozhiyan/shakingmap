package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class VisitorServiceTest extends ShakingmapApplicationTests{
    @Autowired
    private VisitorService visitorService;

    @Test
    public void test1() throws IOException {
        visitorService.locationInfo("湖北","宜昌");
        System.out.println(123);
    }
}