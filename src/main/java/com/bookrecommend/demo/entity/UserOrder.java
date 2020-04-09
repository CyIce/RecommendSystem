package com.bookrecommend.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// 用户订单表
@Entity
@Table(name = "user_order")
public class UserOrder {

    @Id
    @GeneratedValue
    private Integer id;

    // 创建时间
    @Column(name = "create_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 付款时间
    @Column(name = "payment_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    // 发货时间
    @Column(name = "deliver_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliverTime;

    // 发货时间
    @Column(name = "receive_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    // 付款状态
    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus;

    // 发货状态
    @Column(name = "deliver_status", nullable = false)
    private Boolean deliverStatus;

    // 签收状态
    @Column(name = "receive_status", nullable = false)
    private Boolean receiveStatus;

    // 购物订单
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoping_order")
    private List<ShopingOrder> shopingOrderList;


}
