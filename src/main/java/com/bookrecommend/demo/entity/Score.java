package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 评分表
@Data
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 用户id
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 书籍id
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 用户对书籍的评分
    @Column(name = "score", nullable = false, length = 11)
    private Integer score;

    // 用户评分的时间
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Score() {
    }

    public Score(Integer userId, Integer bookId, Integer score, Date date) {
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
        this.date = date;
    }
}
