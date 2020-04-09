package com.bookrecommend.demo.entity;


import javax.persistence.*;

// 用户标签表
@Entity
@Table(name = "user_label")
public class UserLabel {

    @Id
    @GeneratedValue
    private Integer id;

    // 标签
    @OneToOne
    @JoinColumn(name = "label")
    private Label label;

    // 用户对标签的喜爱程度
    @Column(name = "value", nullable = false, length = 11)
    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
