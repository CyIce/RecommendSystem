package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 收藏表
@Entity
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // 收藏日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}

