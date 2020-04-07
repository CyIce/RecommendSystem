package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


// 用户表
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 11)
    private Integer id;

    // 姓名
    @Column(name = "name",unique = true,nullable = false,length = 31)
    private  String name;

    // 年龄
    @Column(name = "age",nullable = false,length = 11)
    private Integer age;

    // 性别，0为女性，1为男性
    @Column(name = "gender",nullable = false)
    private Boolean gender;

    // 头像的url
    @Column(name = "photo",nullable = false,length = 255)
    private String photo;

    // 用户简介
    @Column(name = "introduction",columnDefinition = "text",length = 2000)
    private String introduction;

    // 手机号码
    @Column(name = "phone_number",nullable = false,length = 31)
    private String phoneNumber;


    // 邮箱地址
    @Column(name = "email",nullable = false,length = 31)
    private String email;

    // 注册日期
    @Column(name = "registration_date",nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;

//    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date registrationDate;

    // 用户消息
    @OneToMany(fetch = FetchType.LAZY)
    private List<Message> messageList;
}
