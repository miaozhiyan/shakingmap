package com.douyu.supermap.shakingmap.common.vo.inner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentTemplate {

    private Long id;

    private Long createTime;

    private Long uid;

    private String contentUrl;

    private String contentNote;

    private Integer favoriteCount=0;

    private Integer status;

    private String locationCountry;

    private String locationRegion;

    private String locationCity;

    private Integer type;

    private String avatar;

    private String nickname;

    private Double baiduMapLongtitue;

    private Double baiduMapLatitude;
}
