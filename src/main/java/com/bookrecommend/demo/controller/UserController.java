package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.entity.Collection;
import com.bookrecommend.demo.entity.ShopingCart;
import com.bookrecommend.demo.respository.*;
import com.bookrecommend.demo.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
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

    @Autowired
    private CollectionRepository collectionRepository;


    @GetMapping(value = "/user")
    public String index(Model model, HttpServletRequest request) {

        int userId = Utils.SetLoginInfo(model, request, userRepository);
        UserOnly user = userRepository.findUserBaseInfoByUserId(userId);
        model.addAttribute("user", user);

        Pageable pageable = PageRequest.of(0, 8);

        Page<BookOnly> wantBooks = bookRepository.findCollectionsByUserIdOrderByDate(pageable, userId, 1);
        Page<BookOnly> readingBooks = bookRepository.findCollectionsByUserIdOrderByDate(pageable, userId, 2);
        Page<BookOnly> haveReadBooks = bookRepository.findCollectionsByUserIdOrderByDate(pageable, userId, 3);
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

    @GetMapping(value = "/user/collection")
    public String wantBook(@RequestParam("status") Integer status,
                           @RequestParam("book_order_type") String bookOrderType,
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
            booksPage = bookRepository.findCollectionsAllInfoByUserIdOrderByDate(pageable, userId, status);
        } else {
            Sort sort = Sort.by(Sort.Order.desc(bookOrderType));
            pageable = PageRequest.of(offset, limit, sort);
            booksPage = bookRepository.findCollectionsAllInfoByUserId(pageable, userId, status);
        }

        List<BookOnly> books = booksPage.toList();

        for (BookOnly book : books) {
            List<AuthorOnly> authors = authorRepository.findAuthorsByBookId(book.getId());
            book.setAuthors(authors);
            book.setExistInShopingCart(shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, book.getId()));
        }

        model.addAttribute("collectedStatus", status);
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


    @PostMapping(value = "/user/addcollection")
    @ResponseBody
    public String addCollection(@RequestBody JSONObject json,
                                HttpServletRequest request) {

        Integer userId = Utils.GetUserId(request);
        Integer bookId = json.getInteger("bookId");
        Integer collectedStatus = json.getInteger("collectedStatus");

        log.info("bookId:", bookId);

        Collection c = collectionRepository.findCollectionByUserIdAndBookId(userId, bookId);
        if (c == null) {
            c = new Collection(userId, bookId, collectedStatus, new Date());
        } else if (c.getStatus() != collectedStatus) {
            c.setStatus(collectedStatus);
            c.setDate(new Date());
        } else {
            return "-1";
        }
        collectionRepository.save(c);
        return "1";

    }


    @PostMapping(value = "/user/deletecollection")
    @ResponseBody
    public String deleteCollection(@RequestBody JSONObject json,
                                   HttpServletRequest request) {

        Integer userId = Utils.GetUserId(request);
        Integer bookId = json.getInteger("bookId");
        Integer collectedStatus = json.getInteger("collectedStatus");

        Collection c = collectionRepository.findCollectionByUserIdAndBookId(userId, bookId);
        if (c != null) {
            collectionRepository.delete(c);
            return "1";
        } else {
            log.info("该书籍未被收藏");
            return "-1";
        }
    }


    @PostMapping(value = "/user/addshopingcart")
    @ResponseBody
    public String addShopingCart(@RequestBody JSONObject json,
                                 HttpServletRequest request) {

        Integer userId = Utils.GetUserId(request);
        Integer bookId = json.getInteger("bookId");

        if (shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, bookId)) {
            return "-1";
        } else {
            float price = bookRepository.getOne(bookId).getPrice();
            ShopingCart s = new ShopingCart(userId, bookId, 1, price, new Date());
            shopingCartRepository.save(s);
            return "1";
        }
    }

    @PostMapping(value = "/user/deleteshopingcart")
    @ResponseBody
    public String deleteShopingCart(@RequestBody JSONObject json,
                                    HttpServletRequest request) {

        Integer userId = Utils.GetUserId(request);
        Integer bookId = json.getInteger("bookId");

        ShopingCart s = shopingCartRepository.findShopingCartByUserIdAndBookId(userId, bookId);
        if (s == null) {
            log.info("该书籍不在购物车中");
            return "-1";
        } else {
            shopingCartRepository.delete(s);
            log.info("删除成功");
            return "1";
        }

    }

}
