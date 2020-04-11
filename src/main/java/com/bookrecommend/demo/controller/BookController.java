package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.entity.Book;
import com.bookrecommend.demo.respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    public Book getBook(@RequestParam(value = "book_id") Integer bookId) {
        Book book = bookRepository.findBookById(bookId);
        book.setConnectStatus(true);
        return book;

    }

//    @PostMapping()
//    public int addBook(@RequestParam("name_cn") String nameCn,
//                       @RequestParam("name_Eng") String nameEng,
//                       @RequestParam(""))
}
