package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.Data.BookLabelOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.respository.AuthorRepository;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.CommentRepository;
import com.bookrecommend.demo.respository.UserRepository;
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
public class BookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    public String getBook(@RequestParam("book_id") Integer bookId,
                          @RequestParam("comment_order_type") String commentOrderType,
                          @RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          Model model) {
        offset -= 1;

        Sort sort = Sort.by(Sort.Order.desc(commentOrderType));
        Pageable pageable = PageRequest.of(offset, limit, sort);


        BookOnly book = bookRepository.findBookByBookId(bookId);
        book.setLabels(bookRepository.findBookLabelsByBookId(bookId));
        book.setAuthors(authorRepository.findAuthorsInfoByBookId(bookId));
        model.addAttribute("book", book);

        Page<CommentOnly> comments = commentRepository.findCommentsByBookId(pageable, bookId);

        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("commentsNumber", comments.getTotalElements());
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("comments", comments.toList());

        boolean isCollected = false;
        boolean isWant = false;
        boolean isReading = false;
        boolean isHaveRead = false;
        boolean inShopingCart = false;

        if (userRepository.isCollectedByUserIdAndBookId(1, bookId) > 0) {
            isCollected = true;
        }
        model.addAttribute("isCollected", isCollected);
        if (userRepository.isWantByUserIdAndBookId(1, bookId) > 0) {
            isWant = true;
        }
        model.addAttribute("isWant", isWant);
        if (userRepository.isReadingByUserIdAndBookId(1, bookId) > 0) {
            isReading = true;
        }
        model.addAttribute("isReading", isReading);
        if (userRepository.isHaveReadByUserIdAndBookId(1, bookId) > 0) {
            isHaveRead = true;
        }
        model.addAttribute("isHaveRead", isHaveRead);
        if (userRepository.inShopingCartByUserIdAndBookId(1, bookId) > 0) {
            inShopingCart = true;
        }
        model.addAttribute("inShopingCart", inShopingCart);

        return "book";
    }
}
