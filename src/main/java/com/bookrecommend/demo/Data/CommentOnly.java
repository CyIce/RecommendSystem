package com.bookrecommend.demo.Data;

import com.bookrecommend.demo.util.Utils;
import lombok.Data;

import java.util.Date;

@Data
public class CommentOnly {

    private Integer userId;

    private String userName;

    private String comment;

    private Integer hot;

    private Integer score;

    private Date date;

    public CommentOnly(Integer userId, String userName, String comment, Integer hot, Date date) {
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.hot = hot;
        this.date = date;
    }

    public String getDate() {
        return Utils.Date2String(date, true);
    }
}
