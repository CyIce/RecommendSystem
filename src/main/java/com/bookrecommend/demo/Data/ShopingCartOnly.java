package com.bookrecommend.demo.Data;

import com.bookrecommend.demo.util.Utils;
import lombok.Data;

import java.util.Date;

@Data
public class ShopingCartOnly {


    private Integer id;

    private Integer bookId;

    private String bookName;

    private float price;

    private String bookPicture;

    private Integer number;

    private Date date;


    public ShopingCartOnly(Integer id, Integer bookId, String bookName, float price, String bookPicture, Integer number, Date date) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.bookPicture = bookPicture;
        this.number = number;
        this.date = date;
    }

    public String getDate() {
        return Utils.Date2String(date, false);
    }
}
