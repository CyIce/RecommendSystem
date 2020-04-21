package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping(value = "/user")
    public String index(@RequestParam("user_id") Integer userId, Model model) {


        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        Pageable pageable = PageRequest.of(0, 8);

        Page<BookOnly> readingBooks = bookRepository.findReadingBooksByUserId(pageable, userId);
        Page<BookOnly> wantBooks = bookRepository.findReadingBooksByUserId(pageable, userId);
        Page<BookOnly> haveReadBooks = bookRepository.findReadingBooksByUserId(pageable, userId);
        model.addAttribute("readingNum", readingBooks.getTotalElements());
        model.addAttribute("readingBooks", readingBooks.toList());
        model.addAttribute("wantNum", wantBooks.getTotalElements());
        model.addAttribute("wantBooks", wantBooks.toList());
        model.addAttribute("haveReadNum", haveReadBooks.getTotalElements());
        model.addAttribute("haveReadBooks", haveReadBooks.toList());

        pageable = PageRequest.of(0, 10);
        Page<CommentOnly> comments = userRepository.findCommentsByUserId(pageable, userId);
        model.addAttribute("commentNum", comments.getTotalElements());
        model.addAttribute("comments", comments.toList());

        return "user";
    }
}
