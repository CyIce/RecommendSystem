package com.bookrecommend.demo.Data;

import com.bookrecommend.demo.util.Utils;
import lombok.Data;

import java.util.Date;

@Data
public class CommentOnly {

    private Integer userId;

    private String userName;

    private String userPhoto;

    private Integer bookId;

    private String bookName;

    private String bookPicture;

    private String comment;

    private Integer hot;

    private Integer score;

    private Date date;

    public CommentOnly(Integer userId, String userName, String userPhoto, String comment, Integer score, Integer hot, Date date) {
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.comment = comment;
        this.score = score;
        this.hot = hot;
        this.date = date;
    }

    public CommentOnly(Integer bookId, String bookName, String bookPicture, String comment, Integer score, Date date) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPicture = bookPicture;
        this.comment = comment;
        this.score = score;
        this.date = date;
    }

    public String getDate() {
        return Utils.Date2String(date, false);
    }


}
