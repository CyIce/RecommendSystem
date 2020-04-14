package com.bookrecommend.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


// 作者表
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 中文名
    @Column(name = "name_cn", length = 31)
    private String nameCn;

    // 英文名
    @Column(name = "name_eng", length = 31)
    private String nameEng;

    // 头像
    @Column(name = "photo", nullable = false, length = 255)
    private String photo;

    // 简介
    @Column(name = "introduction", columnDefinition = "text", length = 2000)
    private String introduction;

    // 评分
    @Column(name = "score", nullable = false, length = 11, precision = 4)
    private Float score;

    // 作者的书籍
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "authot_to_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookList;

    public Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
