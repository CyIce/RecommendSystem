package com.bookrecommend.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


// 用户表
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 11)
    private Integer id;

    // 姓名
    @Column(name = "name",unique = true,nullable = false,length = 31)
    private  String name;

    // 年龄
    @Column(name = "age",nullable = false,length = 11)
    private Integer age;

    // 性别，0为女性，1为男性
    @Column(name = "gender",nullable = false)
    private Boolean gender;

    // 头像的url
    @Column(name = "photo",nullable = false,length = 255)
    private String photo;

    // 用户简介
    @Column(name = "introduction",columnDefinition = "text",length = 2000)
    private String introduction;

    // 手机号码
    @Column(name = "phone_number",nullable = false,length = 31)
    private String phoneNumber;

    // 邮箱地址
    @Column(name = "email",nullable = false,length = 31)
    private String email;

    // 注册日期
    @Column(name = "registration_date",nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registrationDate;


    // 用户消息
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Message> messageList;

    // 用户评论
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Comment> commentList;

    // 用户收藏
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Collection> collectionList;

    // 用户购物车
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<ShopingCart> shopingCartList;

    // 推荐书籍
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Recommend> recommendList;

    // 阅读记录
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<ReadingRecord> readingRecordList;

    // 用户订单
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserOrder> userOrderList;

    // 退款记录
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<RefundRecord> refundRecordList;

    // 用户标签
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserLabel> userLabelList;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    public List<ShopingCart> getShopingCartList() {
        return shopingCartList;
    }

    public void setShopingCartList(List<ShopingCart> shopingCartList) {
        this.shopingCartList = shopingCartList;
    }

    public List<Recommend> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<Recommend> recommendList) {
        this.recommendList = recommendList;
    }

    public List<ReadingRecord> getReadingRecordList() {
        return readingRecordList;
    }

    public void setReadingRecordList(List<ReadingRecord> readingRecordList) {
        this.readingRecordList = readingRecordList;
    }

    public List<UserOrder> getUserOrderList() {
        return userOrderList;
    }

    public void setUserOrderList(List<UserOrder> userOrderList) {
        this.userOrderList = userOrderList;
    }

    public List<RefundRecord> getRefundRecordList() {
        return refundRecordList;
    }

    public void setRefundRecordList(List<RefundRecord> refundRecordList) {
        this.refundRecordList = refundRecordList;
    }

    public List<UserLabel> getUserLabelList() {
        return userLabelList;
    }

    public void setUserLabelList(List<UserLabel> userLabelList) {
        this.userLabelList = userLabelList;
    }
}
