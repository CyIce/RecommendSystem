package com.bookrecommend.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kind")
public class Kind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签名称
    @Column(name = "kind", nullable = false, unique = true, length = 31)
    private String kind;

    // 标签热度
    @Column(name = "hot", nullable = false, length = 11)
    private Integer hot;

    public Kind() {
    }
}
