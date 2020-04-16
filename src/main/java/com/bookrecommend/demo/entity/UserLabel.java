package com.bookrecommend.demo.entity;


import javax.persistence.*;

// 用户标签表
@Entity
@Table(name = "user_label")
public class UserLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签
    @Column(name = "label_id", nullable = false, length = 11)
    private Integer labelId;

    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 用户对标签的喜爱程度
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public UserLabel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
