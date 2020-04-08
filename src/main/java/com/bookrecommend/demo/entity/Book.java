package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


// 书籍表
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    // 书籍中文名称
    @Column(name = "name_cn",nullable = false,length = 31)
    private String nameCn;

    // 书籍英文名称
    @Column(name = "name_eng",length = 31)
    private String nameEng;

    // 书籍封面url
    @Column(name = "photo",nullable = false,length = 255)
    private String photo;

    // 书籍的作者
    @ManyToMany
    @JoinTable(name = "authot_to_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    List<Author> authorList;

    // 书籍出版社
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "press_id")
    private Press press;

    // 出版日期
    @Column(name = "publication_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;

    // 书籍字数
    @Column(name = "word_count", nullable = false, length = 11)
    private int wordCount;

    // 书籍售价
    @Column(name = "price", length = 11, precision = 4)
    private float price;

    // 书籍评分
    @Column(name = "score", nullable = false, length = 11, precision = 4)
    private float score;

    // 书籍简介
    @Column(name = "introduction", columnDefinition = "text", length = 2000)
    private String introduction;

    // 书籍热度
    @Column(name = "hot", nullable = false, length = 11)
    private Integer hot;

    // 书籍加入数据库时间
    @Column(name = "create_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 书籍最后修改时间
    @Column(name = "modified_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    // 书籍评论
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> commentList;

}
