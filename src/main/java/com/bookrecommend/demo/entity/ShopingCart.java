package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 购物车表
@Entity
@Table(name = "shopingcart")
public class ShopingCart {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // 单价
    @Column(name = "price", nullable = false, length = 11, precision = 4)
    private float price;

    // 数量
    @Column(name = "number", nullable = false, length = 11)
    private Integer number;

    // 加入购物车的日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
