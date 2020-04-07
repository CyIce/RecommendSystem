package com.bookrecommend.demo.entity;


import javax.persistence.*;

// 标签表
@Entity
@Table(name = "label")
public class Label {

    @Id
    @GeneratedValue
    private Integer id;

    // 标签名称
    @Column(name = "label", nullable = false, unique = true, length = 31)
    private String label;

    // 标签热度
    @Column(name = "hot", nullable = false, length = 11)
    private Integer hot;
}
