package com.bookrecommend.demo.entity;

import lombok.Data;

import javax.persistence.*;


// 作者表
@Data
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 中文名
    @Column(name = "name_cn", length = 31)
    private String nameCn;

    // 英文名
    @Column(name = "name_eng", length = 31)
    private String nameEng;

    // 头像
    @Column(name = "photo", nullable = false, length = 255)
    private String photo;

    // 简介
    @Column(name = "introduction", columnDefinition = "text", length = 2000)
    private String introduction;

    // 评分
    @Column(name = "score", nullable = false, length = 11, precision = 4)
    private Float score;

    public Author() {
    }

}
