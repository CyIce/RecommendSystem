package com.bookrecommend.demo.Data;


import lombok.Data;

@Data
public class BookLabelOnly {

    private Integer labelId;

    private String label;

    public BookLabelOnly(Integer labelId, String label) {
        this.labelId = labelId;
        this.label = label;
    }
}
