package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.vo.res.ResultVo;

import java.io.IOException;

public interface IVisitorService {
    ResultVo locationInfo(String address, String city) throws IOException;
}
