package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


// 出版社表
@Entity
@Table(name = "press")
public class Press {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 出版社名称
    @Column(name = "name", nullable = false, unique = true, length = 31)
    private String name;

    // 简介
    @Column(name = "introduction", columnDefinition = "text", length = 2000)
    private String introduction;

    // 成立日期
    @Column(name = "establish_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date establishDate;


    public Press() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

}
