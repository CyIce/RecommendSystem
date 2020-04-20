package com.bookrecommend.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_kind")
public class UserKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签
    @Column(name = "kind_id", nullable = false, length = 11)
    private Integer kindId;

    // 用户
    @Column(name = "user_id", nullable = false, length = 11)
    private Integer userId;

    // 用户对类别的喜爱程度
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public UserKind() {
    }

}
