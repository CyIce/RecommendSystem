package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.Data.BookLabelOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.entity.Kind;
import com.bookrecommend.demo.entity.Label;
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

import java.util.ArrayList;
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

    @Autowired
    private ScoreRepository scoreRepository;

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
            List<BookLabelOnly> labels = bookRepository.findBookKindsByBookId(bookOnly.getId());
            bookOnly.setKinds(labels);
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
        book.setKinds(bookRepository.findBookKindsByBookId(bookId));
        book.setAuthors(authorRepository.findAuthorsInfoByBookId(bookId));
        model.addAttribute("book", book);

        Page<CommentOnly> comments = commentRepository.findCommentsByBookId(pageable, bookId);

        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("commentsNumber", comments.getTotalElements());
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("comments", comments.toList());
        Integer scoreNumber = scoreRepository.findScoreNumberByBookId(bookId);
        model.addAttribute("scoreNumber", scoreNumber);
        if (scoreNumber == 0) {
            scoreNumber = 1;
        }

        List<Double> starsPercent = new ArrayList<Double>();
//        float score=0;
        for (int i = 1; i <= 5; i++) {
            starsPercent.add(100.0 * scoreRepository.findScoreNumberByBookIdAndScore(bookId, i * 2) / scoreNumber);
//            score+=(starsPercent.get(i-1)*i*2);
        }

        model.addAttribute("fiveStarPercent", Utils.DoubleToFormat(starsPercent.get(4)));
        model.addAttribute("fourStarPercent", Utils.DoubleToFormat(starsPercent.get(3)));
        model.addAttribute("threeStarPercent", Utils.DoubleToFormat(starsPercent.get(2)));
        model.addAttribute("towStarPercent", Utils.DoubleToFormat(starsPercent.get(1)));
        model.addAttribute("oneStarPercent", Utils.DoubleToFormat(starsPercent.get(0)));

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


    @GetMapping(value = "/library")
    public String getLibrary(@RequestParam("book_order_type") String bookOrderType,
                             @RequestParam("offset") Integer offset,
                             @RequestParam("limit") Integer limit, Model model) {

        offset -= 1;

        Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
        Pageable pageable = PageRequest.of(offset, limit, sort);


        List<Kind> kinds = bookRepository.findAllKind();
        List<Label> labels = bookRepository.findAllLabel();

        model.addAttribute("kinds", kinds);
        model.addAttribute("kindsNumber", kinds.size());
        model.addAttribute("labels", labels);
        model.addAttribute("labelsNumber", labels.size());

        Page<BookOnly> booksPage = bookRepository.findAllBook(pageable);
        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            book.setAuthors(authorRepository.findAuthorsInfoByBookId(book.getId()));
            book.setKinds(bookRepository.findBookKindsByBookId(book.getId()));
            book.setLabels(bookRepository.findBookLabelsByBookId(book.getId()));
        }

        model.addAttribute("booksNumber", booksPage.getTotalElements());
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("bookOrderType", bookOrderType);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("books", books);


        return "library";
    }
}
