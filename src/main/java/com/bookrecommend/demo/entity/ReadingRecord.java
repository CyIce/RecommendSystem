package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 阅读记录表
@Entity
@Table(name = "reading_record")
public class ReadingRecord {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // 上次阅读到的位置
    @Column(name = "position", nullable = false, length = 11)
    private Integer position;

    // 上次阅读的日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    // 本书阅读的总时长
    @Column(name = "reading_time", nullable = false, length = 11)
    private Integer readingTime;
}