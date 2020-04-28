package com.bookrecommend.demo.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "author_to_book")
public class AuthorToBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author_id", nullable = false, length = 11)
    private Integer authorId;

    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    public AuthorToBook(Integer authorId, Integer bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }
}
