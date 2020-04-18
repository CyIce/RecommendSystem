package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 推荐表
@Entity
@Table(name = "recommend")
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 书籍推荐的优先级别
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    // 日期日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Recommend() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
