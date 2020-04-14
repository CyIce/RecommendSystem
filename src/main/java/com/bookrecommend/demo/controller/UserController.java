package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.Author;
import com.bookrecommend.demo.entity.Book;
import com.bookrecommend.demo.entity.Collection;
import com.bookrecommend.demo.entity.ReadingRecord;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.CollectionRepository;
import com.bookrecommend.demo.respository.ReadingRecordRepository;
import com.bookrecommend.demo.respository.UserRepository;
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
            Collection c = collectionRepository.findCollectionsByUserIdAndBookId(userId, bookId);
            collectionRepository.deleteById(c.getId());
            return "1";
        } else {
            return "-1";
        }
    }


    // 获取用户的阅读记录
    @PostMapping("/readingrecord")
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
            JSONObject temp = new JSONObject();
            temp.put("bookId", r.getBook().getId());
            temp.put("bookNameCn", r.getBook().getNameCn());
            temp.put("bookNameEng", r.getBook().getNameEng());
            temp.put("bookPhoto", r.getBook().getPhoto());
            temp.put("rate", r.getPosition() * 1.0 / r.getBook().getWordCount());
            temp.put("readingTime", r.getReadingTime());
            temp.put("date", Utils.Date2String(r.getDate(), true));
            List<Author> authorList = r.getBook().getAuthorList();
            JSONObject jsonAuthors = new JSONObject();
            for (int j = 0; j < authorList.size(); j++) {
                JSONObject json = new JSONObject();
                json.put("Id", authorList.get(j).getId());
                json.put("nameCn", authorList.get(j).getNameCn());
                json.put("nameEng", authorList.get(j).getNameEng());
                jsonAuthors.put(Integer.toString(j), json);
            }
            temp.put("authorList", jsonAuthors);
            jsonRecords.put(Integer.toString(i), temp);
        }

        return jsonRecords.toJSONString();
    }

    // 判断书籍是否已被用户收藏
    public boolean isCollected(Integer userId, Integer book_id) {
        Collection c = collectionRepository.findCollectionsByUserIdAndBookId(userId, book_id);
        return c != null;
    }
}
