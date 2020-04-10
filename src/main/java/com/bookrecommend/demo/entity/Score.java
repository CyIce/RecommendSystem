package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 评分表
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue
    private Integer id;

    // 用户id
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 书籍id
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer book_id;

    // 用户对书籍的评分
    @Column(name = "score", nullable = false, length = 11)
    private Integer score;

    // 用户评分的时间
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Score() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
