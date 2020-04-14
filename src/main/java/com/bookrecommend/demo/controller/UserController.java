package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.Author;
import com.bookrecommend.demo.entity.Collection;
import com.bookrecommend.demo.entity.ReadingRecord;
import com.bookrecommend.demo.respository.CollectionRepository;
import com.bookrecommend.demo.respository.ReadingRecordRepository;
import com.bookrecommend.demo.respository.UserRepository;
import com.bookrecommend.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ReadingRecordRepository readingRecordRepository;

    // 获取用户的收藏
    @PostMapping("/collection")
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
            temp.put("bookId", collection.getBook().getId());
            temp.put("nameCn", collection.getBook().getNameCn());
            temp.put("nameEng", collection.getBook().getNameEng());
            temp.put("photo", collection.getBook().getPhoto());
            temp.put("date", Utils.FormatDate(collection.getDate(), true));
            jsonBooks.put(Integer.toString(i), temp);
        }

        return jsonBooks.toJSONString();
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
            temp.put("date", Utils.FormatDate(r.getDate(), true));
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
}
