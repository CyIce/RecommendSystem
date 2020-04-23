package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 购物车表
@Data
@Entity
@Table(name = "shoping_cart")
public class ShopingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

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

    public ShopingCart() {
    }

    public ShopingCart(Integer userId, Integer bookId, Integer number, float price, Date date) {
        this.bookId = bookId;
        this.userId = userId;
        this.price = price;
        this.number = number;
        this.date = date;
    }
}
