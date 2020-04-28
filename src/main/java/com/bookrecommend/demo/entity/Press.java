package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


// 出版社表
@Data
@Entity
@Table(name = "press")
public class Press {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 出版社名称
    @Column(name = "name", nullable = false, unique = true, length = 128)
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

    public Press(String name, String introduction, Date establishDate) {
        this.name = name;
        this.introduction = introduction;
        this.establishDate = establishDate;
    }
}
