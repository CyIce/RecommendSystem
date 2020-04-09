package com.bookrecommend.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// 消息表
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private Integer id;

    // 消息内容
    @Column(name = "contend", columnDefinition = "text", nullable = false, length = 2000)
    private String contend;

    // 标记消息是否已读
    @Column(name = "state", nullable = false)
    private Boolean state;

    // 表示消息是由系统发出还是由用户发出，true表示由系统发出，false表示由用户发出
    @Column(name = "type", nullable = false)
    private Boolean type;

    // 消息的发送日期
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContend() {
        return contend;
    }

    public void setContend(String contend) {
        this.contend = contend;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
