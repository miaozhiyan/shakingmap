package com.douyu.supermap.shakingmap.common.utils;

import com.douyu.supermap.shakingmap.common.vo.inner.IpInfoVo;
import org.junit.Test;

import static org.junit.Assert.*;

public class IPUtilsTest {
    @Test
    public void getIpInfo() throws Exception {
        //61.136.201.000
        IpInfoVo ipInfoVo = IPUtils.getIpInfo("61.136.201.2");
        System.out.println(123);
    }

}