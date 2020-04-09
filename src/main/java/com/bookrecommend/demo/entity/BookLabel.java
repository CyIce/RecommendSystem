package com.bookrecommend.demo.entity;


import javax.persistence.*;

// 书籍标签表
@Entity
@Table(name = "book_label")
public class BookLabel {

    @Id
    @GeneratedValue
    private Integer id;

    // 标签
    @OneToOne
    @JoinColumn(name = "label")
    private Label label;

    // 该标签与书籍的相似程
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
