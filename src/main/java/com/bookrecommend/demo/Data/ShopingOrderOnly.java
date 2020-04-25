package com.bookrecommend.demo.Data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopingOrderOnly {
    private Integer id;

    private Integer bookId;

    private String bookName;

    private String bookPicture;

    private float price;

    private Integer number;

    private float totalMoney;


    public ShopingOrderOnly(Integer id, Integer bookId, String bookName, String bookPicture, float price, Integer number) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPicture = bookPicture;
        this.price = price;
        this.number = number;
    }

    public float getTotalMoney() {
        BigDecimal bigDecimal = new BigDecimal(totalMoney);
        return bigDecimal.setScale(2, BigDecimal.ROUND_UP).floatValue();
    }
}
