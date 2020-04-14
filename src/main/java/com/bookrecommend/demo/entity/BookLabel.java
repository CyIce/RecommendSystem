package com.bookrecommend.demo.entity;


import javax.persistence.*;

// 书籍标签表
@Entity
@Table(name = "book_label")
public class BookLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 标签
//    @ManyToOne
//    @JoinColumn(name = "label_id")
//    private Label label;
    @Column(name = "label_id", nullable = false, length = 11)
    private Integer labelId;

    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    // 该标签与书籍的相似程
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public BookLabel() {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}

