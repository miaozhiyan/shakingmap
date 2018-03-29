package com.douyu.supermap.shakingmap.common.vo.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AddNewContentReq {
    @NotNull(message = "uid不能为空")
    private Long uid;

    private String contentNote;

    private Integer type;
}
