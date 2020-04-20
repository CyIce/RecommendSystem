package com.bookrecommend.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_kind")
public class BookKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签
    @Column(name = "kind_id", nullable = false, length = 11)
    private Integer kindId;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 该类别与书籍的相似程
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public BookKind() {
    }

}
