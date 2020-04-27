package com.bookrecommend.demo.entity;


import lombok.Data;

import javax.persistence.*;

// 书籍标签表
@Data
@Entity
@Table(name = "book_label")
public class BookLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签
    @Column(name = "label_id", nullable = false, length = 11)
    private Integer labelId;

    // 书籍
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 该标签与书籍的相似程
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public BookLabel() {
    }

    public BookLabel(Integer labelId, Integer bookId, Integer value) {
        this.labelId = labelId;
        this.bookId = bookId;
        this.value = value;
    }
}

