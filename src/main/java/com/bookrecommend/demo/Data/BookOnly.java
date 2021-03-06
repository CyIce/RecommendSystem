package com.bookrecommend.demo.Data;


import com.bookrecommend.demo.util.Utils;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookOnly {

    private Integer id;

    private String nameCn;

    private String nameEng;

    private List<AuthorOnly> authors;

    private String bigPicture;

    private String picture;

    private String catalog;

    private Date publicationDate;
    private Date date;

    private int wordCount;

    private float price;

    private float score;

    private String introduction;

    private boolean existInShopingCart;

    private List<BookLabelOnly> labels;
    private List<BookLabelOnly> kinds;

    private String press;

    // 总书籍热度
    private int hot;

    // 周书籍热度
    private int weekHot;

    // 月书籍热度
    private int monthHot;

    // 收藏人数
    private int collectionNum;

    // 想看人数
    private int wantNum;

    // 看过的人数
    private int haveReadNum;

    // 正在看的人数
    private int readingNum;


    public BookOnly() {
    }

    public BookOnly(Integer id) {
        this.id = id;
    }

    public BookOnly(Integer id, String nameCn, String bigPicture) {
        this.id = id;
        this.nameCn = nameCn;
        this.bigPicture = bigPicture;
    }


    public BookOnly(Integer id, String nameCn, String nameEng, String picture) {
        this.id = id;
        this.nameCn = nameCn;
        this.nameEng = nameEng;
        this.picture = picture;
    }


    public BookOnly(Integer id, String nameCn, String picture, int wordCount, float score) {
        this.id = id;
        this.nameCn = nameCn;
        this.picture = picture;
        this.wordCount = wordCount;
        this.score = score;
    }

    public BookOnly(Integer id, String nameCn,
                    String picture, String catalog,
                    Date publicationDate, int wordCount, float price, float score,
                    String introduction, String press) {
        this.id = id;
        this.nameCn = nameCn;
        this.picture = picture;
        this.catalog = catalog;
        this.publicationDate = publicationDate;
        this.wordCount = wordCount;
        this.price = price;
        this.score = score;
        this.introduction = introduction;
        this.press = press;
    }

    public BookOnly(Integer id, String nameCn, String picture, float score, String introduction) {
        this.id = id;
        this.nameCn = nameCn;
        this.picture = picture;
        this.score = score;
        this.introduction = introduction;
    }


    public BookOnly(Integer id, String nameCn, String picture, Date publicationDate, Date date, float price, float score, String press, Integer wordCount) {
        this.id = id;
        this.nameCn = nameCn;
        this.picture = picture;
        this.publicationDate = publicationDate;
        this.date = date;
        this.price = price;
        this.score = score;
        this.press = press;
        this.wordCount = wordCount;
    }

    public BookOnly(Integer id, String nameCn, String bigPicture, String picture, String catalog, Date publicationDate,
                    int wordCount, float price, float score, String introduction, int hot,
                    int weekHot, int monthHot, int collectionNum, int wantNum, int haveReadNum, int readingNum) {
        this.id = id;
        this.nameCn = nameCn;
        this.bigPicture = bigPicture;
        this.picture = picture;
        this.catalog = catalog;
        this.publicationDate = publicationDate;
        this.wordCount = wordCount;
        this.price = price;
        this.score = score;
        this.introduction = introduction;
        this.hot = hot;
        this.weekHot = weekHot;
        this.monthHot = monthHot;
        this.collectionNum = collectionNum;
        this.wantNum = wantNum;
        this.haveReadNum = haveReadNum;
        this.readingNum = readingNum;
    }

    public String getAuthorsName() {
        List<String> nameList = new ArrayList<String>();


        for (int i = 0; i <= 3 && i < authors.size(); i++) {
            nameList.add(authors.get(i).getNameCn());
        }
        return String.join("、", nameList);
    }

    public String labelsToString() {
        List<String> nameList = new ArrayList<String>();

        for (int i = 0; i <= 3 && i < labels.size(); i++) {
            nameList.add(labels.get(i).getLabel());
        }

        return String.join("、", nameList);
    }

    public String kindToString() {
        List<String> nameList = new ArrayList<String>();

        for (int i = 0; i <= 3 && i < kinds.size(); i++) {
            nameList.add(kinds.get(i).getLabel());
        }
        return String.join("、", nameList);
    }

    public String getPrice() {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);
    }

    public String getPublicationDate() {
        return Utils.Date2String(publicationDate, true);
    }

    public String getDate() {
        return Utils.Date2String(date, true);
    }
}
