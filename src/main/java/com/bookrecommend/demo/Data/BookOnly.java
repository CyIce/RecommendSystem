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

    private int wordCount;

    private float price;

    private float score;

    private String introduction;

    private List<BookLabelOnly> labels;

    private String press;

    public BookOnly() {
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

    public String getAuthorsName() {
        List<String> nameList = new ArrayList<String>();

        for (AuthorOnly author : authors) {
            nameList.add(author.getNameCn());
        }
        return String.join(" ", nameList);
    }

    public String getPrice() {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);
    }

    public String getPublicationDate() {
        return Utils.Date2String(publicationDate, true);
    }
}
