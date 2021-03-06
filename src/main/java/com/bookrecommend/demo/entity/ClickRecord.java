package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 用户点击记录表
@Data
@Entity
@Table(name = "click_record")
public class ClickRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    @Column(name = "click_times", nullable = false, length = 11)
    private Integer clickTimes;

    // 用户最近一次点击的时间
    @Column(name = "last_click_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastClickTime;

    public ClickRecord() {
    }

}
