package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


// 出版社表
@Entity
@Table(name = "press")
public class Press {

    @Id
    @GeneratedValue
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

    // 出版社出版的书籍
    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> bookList;

}
