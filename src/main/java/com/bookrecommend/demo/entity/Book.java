package com.bookrecommend.demo.entity;


import javax.persistence.*;


// 书籍表
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name_cn",nullable = false,length = 31)
    private String nameCn;

    @Column(name = "name_eng",length = 31)
    private String nameEng;

    @Column(name = "photo",nullable = false,length = 255)
    private String photo;


    private String authorId;

}
