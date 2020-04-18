package com.bookrecommend.demo.Data;


import lombok.Data;

@Data
public class AuthorOnly {

    private Integer id;

    private String nameCn;

    private String nameEng;

    private String photo;

    private String introduction;

    private Float score;

    public AuthorOnly() {
    }

    public AuthorOnly(Integer id, String nameCn) {
        this.id = id;
        this.nameCn = nameCn;
    }

}
