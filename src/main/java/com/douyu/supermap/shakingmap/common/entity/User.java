package com.douyu.supermap.shakingmap.common.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    private Long createTime;

    private String account;

    private String pwd;

    private String nickname;

    private Long exp;

    private String tel;

    private String avatar;

    private Integer status;

    private BigDecimal deposit;
}
