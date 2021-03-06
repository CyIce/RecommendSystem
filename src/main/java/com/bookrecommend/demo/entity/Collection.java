package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 收藏表
@Data
@Entity
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    @Column(name = "status", nullable = false, length = 11)
    private Integer status;

    // 收藏日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Collection() {
    }

    public Collection(Integer userId, Integer bookId, Integer status, Date date) {
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
        this.date = date;
    }
}

