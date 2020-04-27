package com.bookrecommend.demo.Data;

import lombok.Data;

import java.util.Date;

@Data
public class UserOnly {

    private Integer id;

    private String name;

    private Integer age;

    private boolean gender;

    private String photo;

    private String introduction;

    private String phoneNumber;

    private String email;

    private Date registrationDate;


    public String getGender() {
        if (gender) {
            return "男";
        }
        return "女";
    }

    public UserOnly(Integer id, String name, String photo, String introduction) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.introduction = introduction;
    }

    public UserOnly(Integer id) {
        this.id = id;
    }

    public UserOnly(Integer id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public UserOnly(Integer id, String name, Integer age, boolean gender, String photo, String introduction, String phoneNumber, String email, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.photo = photo;
        this.introduction = introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationDate = registrationDate;
    }
}
