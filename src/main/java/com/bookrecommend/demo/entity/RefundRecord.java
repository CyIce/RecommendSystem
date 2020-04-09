package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 退款记录表
@Entity
@Table(name = "refund_record")
public class RefundRecord {

    @Id
    @GeneratedValue
    private Integer id;

    // 购物订单
    @OneToOne
    @JoinColumn(name = "shoping_order")
    private ShopingOrder shopingOrder;

    // 退款状态
    @Column(name = "refund_status", nullable = false, length = 4)
    private Integer refundStatus;

    // 退款日期
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShopingOrder getShopingOrder() {
        return shopingOrder;
    }

    public void setShopingOrder(ShopingOrder shopingOrder) {
        this.shopingOrder = shopingOrder;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
