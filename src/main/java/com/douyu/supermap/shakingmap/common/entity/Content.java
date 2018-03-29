package com.douyu.supermap.shakingmap.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "content")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    private Long createTime;

    private Long uid;

    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "content_note")
    private String contentNote;

    @Column(name = "favorite_count")
    private Integer favoriteCount;

    private Integer status;

    private String location;

    private Integer type;
}
