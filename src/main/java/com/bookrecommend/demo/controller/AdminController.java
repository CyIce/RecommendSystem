package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.respository.AuthorRepository;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping(value = "/admin/user")
    public String getUser(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam("order") String order,
                          Model model) {
        log.info("用户管理");

        offset -= 1;
        Sort sort = Sort.by(Sort.Order.asc(order));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<UserOnly> users = userRepository.findAllUser(pageable);

        model.addAttribute("users", users.toList());
        model.addAttribute("order", order);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("userNumber", users.getTotalElements());

        return "admin_user";
    }

    @GetMapping(value = "/admin/book")
    public String getBook(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam("order") String order,
                          Model model) {
        log.info("书籍管理");

        offset -= 1;
        Sort sort = Sort.by(Sort.Order.asc(order));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<BookOnly> booksJson = bookRepository.findAllBooks(pageable);

        List<BookOnly> books = booksJson.toList();
        for (BookOnly book : books) {
            book.setKinds(bookRepository.findBookKindsByBookId(book.getId()));
            book.setLabels(bookRepository.findBookLabelsByBookId(book.getId()));
            book.setAuthors(authorRepository.findAuthorsInfoByBookId(book.getId()));

        }

        model.addAttribute("books", booksJson.toList());
        model.addAttribute("order", order);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", booksJson.getTotalPages());
        model.addAttribute("userNumber", booksJson.getTotalElements());

        return "admin_book";
    }
}
