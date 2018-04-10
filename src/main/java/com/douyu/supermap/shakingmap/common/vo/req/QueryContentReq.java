package com.douyu.supermap.shakingmap.common.vo.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryContentReq {

    private String locationCountry;

    private String locationRegion;

    private String locationCity;

    private String contentNote;

    private String nickname;
}
