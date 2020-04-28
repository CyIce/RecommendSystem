package com.bookrecommend.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


// 书籍表
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 书籍中文名称
    @Column(name = "name_cn",nullable = false,length = 31)
    private String nameCn;

    // 书籍英文名称
    @Column(name = "name_eng",length = 31)
    private String nameEng;

    // 书籍封面url
    @Column(name = "picture", nullable = false, length = 255)
    private String picture;

    // 大图
    @Column(name = "big_picture", length = 255)
    private String bigPicture;

    // 目录
    @Column(name = "catalog", columnDefinition = "text", length = 5000)
    private String catalog;


    // 书籍出版社
    @Column(name = "press_id", nullable = false, length = 11)
    private Integer pressId;

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

    // 总书籍热度
    @Column(name = "hot", nullable = false, length = 11)
    private Integer hot;

    // 总书籍热度
    @Column(name = "week_hot", nullable = false, length = 11)
    private Integer weekHot;

    // 总书籍热度
    @Column(name = "month_hot", nullable = false, length = 11)
    private Integer monthHot;

    // 收藏人数
    @Column(name = "collection_num", nullable = false, length = 11)
    private Integer collectionNum;

    // 想看人数
    @Column(name = "want_num", nullable = false, length = 11)
    private Integer wantNum;

    // 看过的人数
    @Column(name = "have_read_num", nullable = false, length = 11)
    private Integer haveReadNum;

    // 正在看的人数
    @Column(name = "reading_num", nullable = false, length = 11)
    private Integer readingNum;

    // 书籍加入数据库时间
    @Column(name = "create_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 书籍最后修改时间
    @Column(name = "modified_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;


    public Book() {
    }

}
