package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.respository.*;
import com.bookrecommend.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ShopingCartRepository shopingCartRepository;

    @Autowired
    private CommentRepository commentRepository;


    @GetMapping(value = "/user")
    public String index(Model model, HttpServletRequest request) {

        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        Pageable pageable = PageRequest.of(0, 8);

        Page<BookOnly> readingBooks = bookRepository.findReadingBooksByUserIdOrderByDate(pageable, userId);
        Page<BookOnly> wantBooks = bookRepository.findReadingBooksByUserIdOrderByDate(pageable, userId);
        Page<BookOnly> haveReadBooks = bookRepository.findReadingBooksByUserIdOrderByDate(pageable, userId);
        model.addAttribute("readingNum", readingBooks.getTotalElements());
        model.addAttribute("readingBooks", readingBooks.toList());
        model.addAttribute("wantNum", wantBooks.getTotalElements());
        model.addAttribute("wantBooks", wantBooks.toList());
        model.addAttribute("haveReadNum", haveReadBooks.getTotalElements());
        model.addAttribute("haveReadBooks", haveReadBooks.toList());

        model.addAttribute("userId", userId);

        pageable = PageRequest.of(0, 10);
        Page<CommentOnly> comments = userRepository.findCommentsByUserIdOrderByDate(pageable, userId);
        model.addAttribute("commentNum", comments.getTotalElements());
        model.addAttribute("comments", comments.toList());

        return "user";
    }

    @GetMapping(value = "/user/want")
    public String wantBook(@RequestParam("book_order_type") String bookOrderType,
                           @RequestParam("offset") Integer offset,
                           @RequestParam("limit") Integer limit,
                           Model model,
                           HttpServletRequest request) {
        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        offset -= 1;


        Pageable pageable;
        Page<BookOnly> booksPage;
        if (bookOrderType.equals("date")) {
            pageable = PageRequest.of(offset, limit);
            booksPage = bookRepository.findWantBooksAllInfoByUserIdOrderByDate(pageable, userId);
        } else {
            Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
            pageable = PageRequest.of(offset, limit, sort);
            booksPage = bookRepository.findWantBooksAllInfoByUserId(pageable, userId);
        }

        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(book.getId());
            book.setAuthors(authors);
            book.setExistInShopingCart(shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, book.getId()));
        }

        model.addAttribute("collectType", "want");
        model.addAttribute("userId", userId);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("bookOrderType", bookOrderType);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("booksNum", booksPage.getTotalElements());
        model.addAttribute("books", books);
        return "collection";
    }

    @GetMapping(value = "/user/reading")
    public String readingBook(@RequestParam("book_order_type") String bookOrderType,
                              @RequestParam("offset") Integer offset,
                              @RequestParam("limit") Integer limit,
                              Model model,
                              HttpServletRequest request) {
        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        offset -= 1;


        Pageable pageable;
        Page<BookOnly> booksPage;
        if (bookOrderType.equals("date")) {
            pageable = PageRequest.of(offset, limit);
            booksPage = bookRepository.findReadingBooksAllInfoByUserIdOrderByDate(pageable, userId);
        } else {
            Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
            pageable = PageRequest.of(offset, limit, sort);
            booksPage = bookRepository.findReadingBooksAllInfoByUserId(pageable, userId);
        }

        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(book.getId());
            book.setAuthors(authors);
            book.setExistInShopingCart(shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, book.getId()));
        }

        model.addAttribute("collectType", "reading");
        model.addAttribute("userId", userId);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("bookOrderType", bookOrderType);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("booksNum", booksPage.getTotalElements());
        model.addAttribute("books", books);

        return "collection";
    }


    @GetMapping(value = "/user/have_read")
    public String haveReadBook(@RequestParam("book_order_type") String bookOrderType,
                               @RequestParam("offset") Integer offset,
                               @RequestParam("limit") Integer limit,
                               Model model,
                               HttpServletRequest request) {
        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        offset -= 1;


        Pageable pageable;
        Page<BookOnly> booksPage;
        if (bookOrderType.equals("date")) {
            pageable = PageRequest.of(offset, limit);
            booksPage = bookRepository.findHaveReadBooksAllInfoByUserIdOrderByDate(pageable, userId);
        } else {
            Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
            pageable = PageRequest.of(offset, limit, sort);
            booksPage = bookRepository.findHaveReadBooksAllInfoByUserId(pageable, userId);
        }

        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(book.getId());
            book.setAuthors(authors);
            book.setExistInShopingCart(shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, book.getId()));
        }

        model.addAttribute("collectType", "reading");
        model.addAttribute("userId", userId);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("bookOrderType", bookOrderType);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("booksNum", booksPage.getTotalElements());
        model.addAttribute("books", books);

        return "collection";
    }

    @GetMapping(value = "/user/comment")
    public String userComments(@RequestParam("comment_order_type") String commentOrderType,
                               @RequestParam("offset") Integer offset,
                               @RequestParam("limit") Integer limit,
                               Model model,
                               HttpServletRequest request) {
        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);
        offset -= 1;
        Sort sort = Sort.by(Sort.Order.desc(commentOrderType));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        Page<CommentOnly> comments = commentRepository.findCommentsByUserId(pageable, userId);

        model.addAttribute("userId", userId);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("commentNum", comments.getTotalElements());
        model.addAttribute("comments", comments.toList());

        return "comment";
    }
}
