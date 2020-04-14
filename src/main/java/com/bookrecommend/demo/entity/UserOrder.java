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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "user_order_id")
    private List<ShopingOrder> shopingOrderList;

    public Integer getId() {
        return id;
    }

    public UserOrder() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Boolean getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Boolean deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Boolean getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Boolean receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public List<ShopingOrder> getShopingOrderList() {
        return shopingOrderList;
    }

    public void setShopingOrderList(List<ShopingOrder> shopingOrderList) {
        this.shopingOrderList = shopingOrderList;
    }
}
