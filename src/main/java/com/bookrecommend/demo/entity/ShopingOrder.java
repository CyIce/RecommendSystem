package com.bookrecommend.demo.entity;

import javax.persistence.*;

// 购物订单表
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(Integer userOrderId) {
        this.userOrderId = userOrderId;
    }
}
