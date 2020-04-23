//package com.bookrecommend.demo.entity;
//
//
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.util.Date;
//
//// 阅读记录表
//@Data
//@Entity
//@Table(name = "reading_record")
//public class ReadingRecord {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    // 书籍
//    @Column(name = "book_id", nullable = false, length = 11)
//    private Integer bookId;
//    // 用户
//    @Column(name = "user_id", nullable = false, length = 11)
//    private Integer userId;
//
//    // 上次阅读到的位置
//    @Column(name = "position", nullable = false, length = 11)
//    private Integer position;
//
//    // 上次阅读的日期
//    @Column(name = "date", nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date date;
//
//    // 本书阅读的总时长
//    @Column(name = "reading_time", nullable = false, length = 11)
//    private Integer readingTime;
//
//    public ReadingRecord() {
//    }
//}
