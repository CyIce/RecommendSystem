package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.Collection;
import com.bookrecommend.demo.respository.CollectionRepository;
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

    @PostMapping("/collection")
    public String collectionBooks(@RequestParam("user_id") Integer userId,
                                  @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Collection> collectionList = collectionRepository.findCollectionsByUserId(pageable, userId).toList();

//        List<Book> books = new ArrayList<Book>();
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

}
