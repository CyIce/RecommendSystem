package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 推荐表
@Entity
@Table(name = "recommend")
public class Recommend {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "value", nullable = false, length = 11)
    private Integer value;
    // 日期日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

}
