package com.bookrecommend.demo.entity;

import lombok.Data;

import javax.persistence.*;

// 购物订单表
@Data
@Entity
@Table(name = "shoping_order")
public class ShopingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 购买价格
    @Column(name = "price", nullable = false, length = 11, precision = 4)
    private Float price;

    // 购买数量
    @Column(name = "number", nullable = false, length = 11)
    private Integer number;

    // 用户订单id
    @Column(name = "user_order_id", nullable = false, length = 11)
    private Integer userOrderId;

    public ShopingOrder() {
    }

    public ShopingOrder(Integer bookId, Float price, Integer number, Integer userOrderId) {
        this.bookId = bookId;
        this.price = price;
        this.number = number;
        this.userOrderId = userOrderId;
    }
}
