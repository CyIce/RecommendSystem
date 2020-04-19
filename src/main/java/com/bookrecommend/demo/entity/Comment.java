package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//评论表
@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 评论内容
    @Column(name = "comment", columnDefinition = "text", nullable = false, length = 2000)
    private String comment;

    // 热度
    @Column(name = "hot", nullable = false, length = 11)
    private Integer hot;

    // 评分
    @Column(name = "score", nullable = false, length = 11)
    private Integer score;

    // 评论日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Comment() {
    }
}
