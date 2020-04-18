package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.Data.BookLabelOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.respository.AuthorRepository;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(@RequestParam("user_id") Integer userId, Model model) {

        // 轮播图书籍
        List<BookOnly> top5Books = bookRepository.findBooksByMonthHot().subList(0, 5);
        model.addAttribute("top5Books", top5Books);

        // 推荐书籍
        List<BookOnly> recommendBooks = bookRepository.findRecommendBooksByUserId(userId).subList(0, 10);

        for (BookOnly bookOnly : recommendBooks) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(bookOnly.getId());
            bookOnly.setAuthors(authors);
        }
        model.addAttribute("recommendBooks", recommendBooks);

        // 获取热门书籍
        List<BookOnly> hotBooks = bookRepository.findHotBooks().subList(0, 12);
        for (BookOnly bookOnly : hotBooks) {
            List<BookLabelOnly> labels = bookRepository.findBookLabelsByBookId(bookOnly.getId());
            bookOnly.setLabels(labels);
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(bookOnly.getId());
            bookOnly.setAuthors(authors);
        }
        model.addAttribute("hotBooks", hotBooks);


        return "index";
    }


    @GetMapping(value = "/book")
    public String getBook(@RequestParam("book_id") Integer bookId, Model model) {
        BookOnly book = bookRepository.findBookByBookId(bookId);
        book.setLabels(bookRepository.findBookLabelsByBookId(bookId));
        book.setAuthors(authorRepository.findAuthorsByBookId(bookId));
        model.addAttribute("book", book);


        return "book";
    }
}
