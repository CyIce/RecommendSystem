package com.bookrecommend.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 用户订单表
@Data
@Entity
@Table(name = "user_order")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

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

    public UserOrder() {
    }


    public UserOrder(Integer userId, Date createTime, Boolean paymentStatus, Boolean deliverStatus, Boolean receiveStatus) {
        this.userId = userId;
        this.createTime = createTime;
        this.paymentStatus = paymentStatus;
        this.deliverStatus = deliverStatus;
        this.receiveStatus = receiveStatus;
    }
}
