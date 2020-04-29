package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.*;
import com.bookrecommend.demo.entity.*;
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

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, HttpServletRequest request) {

        int userId = Utils.SetLoginInfo(model, request, userRepository);

        // 轮播图书籍
        List<BookOnly> top5Books = bookRepository.findBooksByMonthHot().subList(0, 5);
        model.addAttribute("top5Books", top5Books);

        // 推荐书籍
        List<BookOnly> recommendBooks = bookRepository.findRecommendBooksByUserId(userId);
        int resNum = 10 - recommendBooks.size();
        if (resNum > 0) {
            recommendBooks.addAll(bookRepository.findHotBooks().subList(20, 20 + resNum));
        } else {
            recommendBooks = recommendBooks.subList(0, 10);
        }

        for (BookOnly bookOnly : recommendBooks) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(bookOnly.getId());
            bookOnly.setAuthors(authors);
        }
        model.addAttribute("recommendBooks", recommendBooks);

        // 获取热门书籍
        List<BookOnly> hotBooks = bookRepository.findHotBooks().subList(0, 12);
        for (BookOnly bookOnly : hotBooks) {
            List<BookLabelOnly> kinds = bookRepository.findBookKindsByBookId(bookOnly.getId());
            List<BookLabelOnly> labels = bookRepository.findBookLabelsByBookId(bookOnly.getId());
            bookOnly.setKinds(kinds);
            bookOnly.setLabels(labels);
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(bookOnly.getId());
            bookOnly.setAuthors(authors);
        }
        model.addAttribute("hotBooks", hotBooks);
        model.addAttribute("userId", userId);

        return "index";
    }


    @GetMapping(value = "/book")
    public String getBook(@RequestParam("book_id") Integer bookId,
                          @RequestParam("comment_order_type") String commentOrderType,
                          @RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          Model model,
                          HttpServletRequest request) {
        int userId = Utils.GetUserId(request);
        offset -= 1;

        Sort sort = Sort.by(Sort.Order.desc(commentOrderType));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        Integer scoreNumber = scoreRepository.findScoreNumberByBookId(bookId);
        model.addAttribute("scoreNumber", scoreNumber);
        if (scoreNumber == 0) {
            scoreNumber = 1;
        }

        List<Double> starsPercent = new ArrayList<Double>();
        float s = 0;
        for (int i = 1; i <= 5; i++) {
            starsPercent.add(100.0 * scoreRepository.findScoreNumberByBookIdAndScore(bookId, i * 2) / scoreNumber);
            s += (starsPercent.get(i - 1) * i * 2) / 100;
        }
        Book b = bookRepository.getOne(bookId);
        b.setScore(s);
        bookRepository.save(b);


        BookOnly book = bookRepository.findBookByBookId(bookId);
        book.setKinds(bookRepository.findBookKindsByBookId(bookId));
        book.setLabels(bookRepository.findBookLabelsByBookId(bookId));
        book.setAuthors(authorRepository.findAuthorsInfoByBookId(bookId));
        model.addAttribute("book", book);

        Page<CommentOnly> comments = commentRepository.findCommentsByBookId(pageable, bookId);

        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("commentsNumber", comments.getTotalElements());
        model.addAttribute("commentOrderType", commentOrderType);
        model.addAttribute("comments", comments.toList());


        model.addAttribute("fiveStarPercent", Utils.DoubleToFormat(starsPercent.get(4)));
        model.addAttribute("fourStarPercent", Utils.DoubleToFormat(starsPercent.get(3)));
        model.addAttribute("threeStarPercent", Utils.DoubleToFormat(starsPercent.get(2)));
        model.addAttribute("towStarPercent", Utils.DoubleToFormat(starsPercent.get(1)));
        model.addAttribute("oneStarPercent", Utils.DoubleToFormat(starsPercent.get(0)));

        int collectedStatus = -1;
        boolean inShopingCart = false;
        boolean isLogin = false;

        if (userId != -1) {
            isLogin = true;

            UserOnly user = userRepository.findUserNameAndPhotoByUserId(userId);
            model.addAttribute("user", user);

            Collection c = userRepository.isCollectedByUserIdAndBookId(userId, bookId);
            if (c != null) {
                collectedStatus = c.getStatus();
            }

            model.addAttribute("collectedStatus", collectedStatus);

            if (userRepository.inShopingCartByUserIdAndBookId(userId, bookId) > 0) {
                inShopingCart = true;
            }
            model.addAttribute("inShopingCart", inShopingCart);

            Score score = scoreRepository.findScoreByUserIdAndBookId(userId, bookId);
            if (score != null) {
                model.addAttribute("userScore", score.getScore());
            } else {
                model.addAttribute("userScore", 0);
            }
        } else {
            model.addAttribute("userScore", 0);
        }
        model.addAttribute("isLogin", isLogin);


        return "book";
    }


    @GetMapping(value = "/library")
    public String getLibrary(@RequestParam("book_order_type") String bookOrderType,
                             @RequestParam("offset") Integer offset,
                             @RequestParam("kind_id") Integer kindId,
                             @RequestParam("keyword") String keyword,
                             @RequestParam("label_id") Integer labelId,
                             @RequestParam("limit") Integer limit,
                             Model model,
                             HttpServletRequest request) {
        int userId = Utils.SetLoginInfo(model, request, userRepository);

        offset -= 1;

        Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
        Pageable pageable = PageRequest.of(offset, limit, sort);


        List<Kind> kinds = bookRepository.findAllKind();
        List<Label> labels = bookRepository.findAllLabel();

        model.addAttribute("kinds", kinds);
        model.addAttribute("kindsNumber", kinds.size());
        model.addAttribute("labels", labels);
        model.addAttribute("labelsNumber", labels.size());

        Page<BookOnly> booksPage;

        if (kindId <= 0 && labelId <= 0) {
            booksPage = bookRepository.findAllBook(pageable, keyword);
        } else if (kindId > 0 && labelId <= 0) {
            model.addAttribute("kindName", kindRepository.getOne(kindId).getKind());
            booksPage = bookRepository.findAllBookByKindId(pageable, kindId, keyword);
        } else if (kindId <= 0) {
            model.addAttribute("labelName", labelRepository.getOne(labelId).getLabel());
            booksPage = bookRepository.findAllBookByLabelId(pageable, labelId, keyword);
        } else {
            model.addAttribute("kindName", kindRepository.getOne(kindId).getKind());
            model.addAttribute("labelName", labelRepository.getOne(labelId).getLabel());
            booksPage = bookRepository.findAllBookByKindIdAndLabelId(pageable, kindId, labelId, keyword);
        }

        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            book.setAuthors(authorRepository.findAuthorsInfoByBookId(book.getId()));
            book.setKinds(bookRepository.findBookKindsByBookId(book.getId()));
            book.setLabels(bookRepository.findBookLabelsByBookId(book.getId()));
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("kindId", kindId);
        model.addAttribute("labelId", labelId);
        model.addAttribute("booksNumber", booksPage.getTotalElements());
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("bookOrderType", bookOrderType);
        model.addAttribute("totalPages", booksPage.getTotalPages());
        model.addAttribute("books", books);


        return "library";
    }
}
