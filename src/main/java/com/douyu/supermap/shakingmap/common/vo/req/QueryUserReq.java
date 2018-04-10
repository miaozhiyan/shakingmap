package com.douyu.supermap.shakingmap.common.vo.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryUserReq {
    private String nickname;

    private Long exp;

    private String account;

    private Integer pageSize;

    private Integer pageNo;
}
