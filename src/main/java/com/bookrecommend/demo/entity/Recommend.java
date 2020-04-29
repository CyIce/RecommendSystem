package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 推荐表
@Data
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
    private Double value;

    // 日期日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Recommend() {
    }

    public Recommend(Integer bookId, Integer userId, Double value, Date date) {
        this.bookId = bookId;
        this.userId = userId;
        this.value = value;
        this.date = date;
    }
}
