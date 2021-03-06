package com.bookrecommend.demo.entity;


import lombok.Data;

import javax.persistence.*;

// 用户标签表
@Data
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

}
