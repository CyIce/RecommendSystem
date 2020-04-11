package com.bookrecommend.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "authot_to_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    List<Author> authorList;

    // 书籍出版社
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
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
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Comment> commentList;

    // 书籍标签
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "book_to_book_label",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "book_label_id"))
    private List<BookLabel> bookLabelList;

    public Book() {
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


    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public Press getPress() {
        return press;
    }

    public void setPress(Press press) {
        this.press = press;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<BookLabel> getBookLabelList() {
        return bookLabelList;
    }

    public void setBookLabelList(List<BookLabel> bookLabelList) {
        this.bookLabelList = bookLabelList;
    }
}
