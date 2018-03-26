package com.douyu.supermap.shakingmap.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name ="role")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_name")
    private String roleName;
}
