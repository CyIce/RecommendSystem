package com.bookrecommend.demo.entity;

import javax.persistence.*;

// 购物订单表
@Entity
@Table(name = "shoping_order")
public class ShopingOrder {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // 购买价格
    @Column(name = "price", nullable = false, length = 11, precision = 4)
    private Float price;

    // 购买数量
    @Column(name = "number", nullable = false, length = 11)
    private Integer number;

}
