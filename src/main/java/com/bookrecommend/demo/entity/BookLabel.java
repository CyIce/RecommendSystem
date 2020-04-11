package com.bookrecommend.demo.entity;


import javax.persistence.*;
import java.util.List;

// 书籍标签表
@Entity
@Table(name = "book_label")
public class BookLabel {

    @Transient
    private boolean connectStatus = false;

    @Id
    @GeneratedValue
    private Integer id;

    // 标签
    @OneToOne
    @JoinColumn(name = "label_id")
    private Label label;

    // 书籍
    @ManyToMany
    @JoinTable(name = "book_to_book_label",
            joinColumns = @JoinColumn(name = "book_label_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> bookList;

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

    public List<Book> getBookList() {
        if (connectStatus) {
            return bookList;
        }
        return null;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public boolean isConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(boolean connectStatus) {
        this.connectStatus = connectStatus;
    }
}

