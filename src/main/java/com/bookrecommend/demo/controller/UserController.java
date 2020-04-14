package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.*;
import com.bookrecommend.demo.respository.*;
import com.bookrecommend.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ReadingRecordRepository readingRecordRepository;

    @Autowired
    private ShopingCartRepository shopingCartRepository;

    // 获取用户的收藏
    @GetMapping("/collection")
    public String collectionBooks(@RequestParam("user_id") Integer userId,
                                  @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Collection> collectionList = collectionRepository.findCollectionsByUserId(pageable, userId).toList();

        JSONObject jsonBooks = new JSONObject();

        for (int i = 0; i < collectionList.size(); i++) {
            Collection collection = collectionList.get(i);
            JSONObject temp = new JSONObject();
            Book book = bookRepository.getOne(collection.getBookId());
            temp.put("bookId", book.getId());
            temp.put("nameCn", book.getNameCn());
            temp.put("nameEng", book.getNameEng());
            temp.put("photo", book.getPhoto());
            temp.put("date", Utils.Date2String(collection.getDate(), true));
            jsonBooks.put(Integer.toString(i), temp);
        }

        return jsonBooks.toJSONString();
    }


    // 收藏一本书籍
    @PostMapping("collection")
    public String addCollection(@RequestParam("user_id") Integer userId,
                                @RequestParam("book_id") Integer bookId,
                                @RequestParam("date") String dateStr) {

        if (isCollected(userId, bookId)) {
            return "-1";
        } else {
            Collection collection = new Collection();
            collection.setBookId(bookId);
            collection.setUserId(userId);
            collection.setDate(Utils.String2Date(dateStr, false));
            collectionRepository.save(collection);
        }

        return "1";
    }

    // 取消收藏
    @DeleteMapping("collection")
    public String deleteCollection(@RequestParam("user_id") Integer userId,
                                   @RequestParam("book_id") Integer bookId) {
        if (isCollected(userId, bookId)) {
            Collection c = collectionRepository.findCollectionByUserIdAndBookId(userId, bookId);
            collectionRepository.delete(c);
            return "1";
        } else {
            return "-1";
        }
    }


    // 获取用户的阅读记录
    @GetMapping("/readingrecord")
    public String readingRecord(@RequestParam("user_id") Integer userId,
                                @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<ReadingRecord> readingRecordList = readingRecordRepository.findReadingRecordsByUserId(pageable, userId).toList();

        JSONObject jsonRecords = new JSONObject();

        for (int i = 0; i < readingRecordList.size(); i++) {
            ReadingRecord r = readingRecordList.get(i);
            Book book = bookRepository.getOne(r.getBookId());
            JSONObject temp = new JSONObject();
            temp.put("bookId", book.getId());
            temp.put("bookNameCn", book.getNameCn());
            temp.put("bookNameEng", book.getNameEng());
            temp.put("bookPhoto", book.getPhoto());
            temp.put("rate", r.getPosition() * 1.0 / book.getWordCount());
            temp.put("readingTime", r.getReadingTime());
            temp.put("date", Utils.Date2String(r.getDate(), true));
            List<Author> authorList = book.getAuthorList();
            JSONObject jsonAuthors = new JSONObject();
            addAuthors2Json(authorList, jsonAuthors);
            temp.put("authorList", jsonAuthors);
            jsonRecords.put(Integer.toString(i), temp);
        }

        return jsonRecords.toJSONString();
    }


    // 为用户添加或修改阅读记录
    @PostMapping("/readingrecord")
    public String addReadingRecord(@RequestParam("user_id") Integer userId,
                                   @RequestParam("book_id") Integer bookId,
                                   @RequestParam("position") Integer position,
                                   @RequestParam("reading_time") Integer readingTime,
                                   @RequestParam("date") String dateStr) {
        ReadingRecord r = readingRecordRepository.findReadingRecordByUserIdAndBookId(userId, bookId);
        if (r == null) {
            ReadingRecord record = new ReadingRecord();
            record.setUserId(userId);
            record.setBookId(bookId);
            record.setPosition(position);
            record.setReadingTime(readingTime);
            record.setDate(Utils.String2Date(dateStr, false));
            readingRecordRepository.save(record);
        } else {
            r.setPosition(position);
            r.setReadingTime(r.getReadingTime() + readingTime);
            r.setDate(Utils.String2Date(dateStr, false));
            readingRecordRepository.save(r);
        }

        return "1";
    }

    // 删除一条阅读记录
    @DeleteMapping("/readingrecord")
    public String deleteReadingRecord(@RequestParam("user_id") Integer userId,
                                      @RequestParam("book_id") Integer bookId) {
        ReadingRecord r = readingRecordRepository.findReadingRecordByUserIdAndBookId(userId, bookId);
        if (r == null) {
            return "-1";
        } else {
            readingRecordRepository.delete(r);
            return "1";
        }
    }


    // 获取用户购物车信息
    @GetMapping("/shopingcart")
    public String getShopingCart(@RequestParam("user_id") Integer userId,
                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<ShopingCart> shopingCartList = shopingCartRepository.findShopingCartsByUserId(pageable, userId).toList();
        JSONObject jsonCart = new JSONObject();
        for (int i = 0; i < shopingCartList.size(); i++) {
            ShopingCart cart = shopingCartList.get(i);
            Book book = bookRepository.getOne(cart.getBookId());
            JSONObject temp = new JSONObject();
            temp.put("id", cart.getId());
            temp.put("bookId", book.getId());
            temp.put("nameCn", book.getNameCn());
            temp.put("nameEng", book.getNameEng());
            temp.put("photo", book.getPhoto());
            temp.put("number", cart.getNumber());
            temp.put("price", cart.getPrice());
            temp.put("date", Utils.Date2String(cart.getDate(), false));
            List<Author> authorList = book.getAuthorList();
            JSONObject jsonAuthors = new JSONObject();
            addAuthors2Json(authorList, jsonAuthors);
            temp.put("authorList", jsonAuthors);
            jsonCart.put(Integer.toString(i), temp);
        }

        return jsonCart.toJSONString();
    }

    // 将书籍添加入购物车
    @PostMapping("/shopingcart")
    public String addShopingCart(@RequestParam("user_id") Integer userId,
                                 @RequestParam("book_id") Integer bookId,
                                 @RequestParam("number") Integer number,
                                 @RequestParam("date") String date) {
        if (shopingCartRepository.existsShopingCartByUserIdAndBookId(userId, bookId)) {
            return "-1";
        }

        ShopingCart cart = new ShopingCart();
        cart.setUserId(userId);
        cart.setBookId(bookId);
        cart.setNumber(number);
        cart.setPrice(bookRepository.getOne(bookId).getPrice());
        cart.setDate(Utils.String2Date(date, false));
        shopingCartRepository.save(cart);
        return "1";
    }

    // 修改购物车中书籍的数量
    @PostMapping("/updateshopingcart")
    public String updateShopingCart(@RequestParam("id") Integer id,
                                    @RequestParam("number") Integer number) {
        if (shopingCartRepository.existsById(id)) {
            ShopingCart cart = shopingCartRepository.getOne(id);
            cart.setNumber(number);
            shopingCartRepository.save(cart);
            return "1";

        } else {
            return "-1";
        }
    }


    // 删除购物车中的书籍
    @DeleteMapping("/shopingcart")
    public String deleteShpingCart(@RequestParam("id") Integer id) {
        if (shopingCartRepository.existsById(id)) {
            shopingCartRepository.deleteById(id);
            return "1";
        } else {
            return "-1";
        }
    }

    // 将作者信息添加到json中
    private void addAuthors2Json(List<Author> authorList, JSONObject jsonAuthors) {
        for (int j = 0; j < authorList.size(); j++) {
            JSONObject json = new JSONObject();
            json.put("Id", authorList.get(j).getId());
            json.put("nameCn", authorList.get(j).getNameCn());
            json.put("nameEng", authorList.get(j).getNameEng());
            jsonAuthors.put(Integer.toString(j), json);
        }
    }


    // 判断书籍是否已被用户收藏
    private boolean isCollected(Integer userId, Integer book_id) {
        Collection c = collectionRepository.findCollectionByUserIdAndBookId(userId, book_id);
        return c != null;
    }

}
