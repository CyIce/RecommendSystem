package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//评论表
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    // 用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 书籍
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // 评论内容
    @Column(name = "comment", columnDefinition = "text", nullable = false, length = 2000)
    private String comment;

    // 评论日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
