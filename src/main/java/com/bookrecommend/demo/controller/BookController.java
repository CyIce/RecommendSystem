package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.Author;
import com.bookrecommend.demo.entity.Book;
import com.bookrecommend.demo.entity.BookLabel;
import com.bookrecommend.demo.entity.Recommend;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.LabelRepository;
import com.bookrecommend.demo.respository.RecommendRepository;
import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    // 根据书籍id获取书籍
    @GetMapping()
    public String getBook(@RequestParam(value = "book_id") Integer bookId) {
        Book book = bookRepository.findBookById(bookId);


        JSONObject jsonBook = book2JsonBook(book);

        return jsonBook.toJSONString();

    }

    // 获取热门书籍
    @GetMapping("/hot")
    public String hotBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("hot"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Book> books = bookRepository.findAll(pageable).toList();

        JSONObject jsonBooks = books2JsonBooks(books);

        return jsonBooks.toJSONString();
    }


    // 获取用户的推荐书籍
    @PostMapping("recommend")
    public String recommendBooks(@RequestParam("user_id") Integer userId,
                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("value"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Recommend> recommendList = recommendRepository.findRecommendsByUserId(pageable, userId).toList();

        List<Book> books = new ArrayList<Book>();
        for (Recommend recommend : recommendList) {
            books.add(recommend.getBook());
        }
        JSONObject jsonBooks = books2JsonBooks(books);

        return jsonBooks.toString();
    }

    // 根据关键字和书籍标签查找书籍
    @PostMapping("search")
    public String searchByKeyword(@RequestParam("keyword") String keyword,
                                  @RequestBody JSONObject labelList,
                                  @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("hot"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Integer> labelIdList = new ArrayList<Integer>();
        for (int i = 0; i < labelList.size(); i++) {
            labelIdList.add(labelList.getJSONObject(Integer.toString(i)).getInteger("labelId"));
        }

        if (labelIdList.size() == 0) {
            labelIdList = null;
        }

        List<Book> books = bookRepository.searchByKeyword(pageable, keyword, labelIdList).toList();
        JSONObject jsonBooks = books2JsonBooks(books);

        return jsonBooks.toJSONString();
    }

    // 将Book对象转化为符合规则的Json对象
    private JSONObject book2JsonBook(Book book) {
        JSONObject jsonBook = JSONObject.parseObject(JSON.toJSONString(book));
        JSONObject authorList = new JSONObject();
        jsonBook.remove("commentList");
        jsonBook.remove("bookLabelList");
        for (int i = 0; i < book.getAuthorList().size(); i++) {
            JSONObject tmp = new JSONObject();
            Author author = book.getAuthorList().get(i);
            tmp.put("authorId", author.getId());
            tmp.put("nameCn", author.getNameCn());
            tmp.put("nameEng", author.getNameEng());
            authorList.put(Integer.toString(i), tmp);
        }
        jsonBook.put("authorList", authorList);

        JSONObject press = new JSONObject();
        press.put("pressId", book.getPress().getId());
        press.put("name", book.getPress().getName());
        jsonBook.put("press", press);

        JSONObject bookLabelList = new JSONObject();
        for (int i = 0; i < book.getBookLabelList().size(); i++) {
            JSONObject tmp = new JSONObject();
            BookLabel bookLabel = book.getBookLabelList().get(i);
            Integer labelId = bookLabel.getLabelId();
            tmp.put("labelId", labelId);
            tmp.put("label", labelRepository.findLabelById(labelId).getLabel());
            tmp.put("value", bookLabel.getValue());
            bookLabelList.put(Integer.toString(i), tmp);
        }
        jsonBook.put("bookLabelList", bookLabelList);
        return jsonBook;
    }

    // 将Book对象列表转化为符合规则的Json对象
    private JSONObject books2JsonBooks(List<Book> books) {
        JSONObject jsonBooks = new JSONObject();
        for (int i = 0; i < books.size(); i++) {
            JSONObject tmp = new JSONObject();
            tmp = book2JsonBook(books.get(i));
            jsonBooks.put(Integer.toString(i), tmp);
        }

        return jsonBooks;
    }
}
