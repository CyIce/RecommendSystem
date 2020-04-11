package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.entity.Author;
import com.bookrecommend.demo.entity.Book;
import com.bookrecommend.demo.entity.BookLabel;
import com.bookrecommend.demo.respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    public String getBook(@RequestParam(value = "book_id") Integer bookId) {
        Book book = bookRepository.findBookById(bookId);


        JSONObject jsonBook = addBookElement(book);

        return jsonBook.toJSONString();

    }

    @GetMapping("/hot")
    public String hotBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        offset = offset < 0 ? 0 : offset;
        Sort sort = Sort.by(Sort.Order.desc("hot"));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        List<Book> books = bookRepository.findAll(pageable).toList();

        JSONObject jsonBooks = new JSONObject();
        for (int i = 0; i < books.size(); i++) {
            JSONObject tmp = new JSONObject();
            tmp = addBookElement(books.get(i));
            jsonBooks.put(Integer.toString(i), tmp);
        }

        return jsonBooks.toJSONString();
    }


    @GetMapping("search")
    public String search() {
        return "";
    }


    private JSONObject addBookElement(Book book) {
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
            tmp.put("labelId", bookLabel.getLabel().getId());
            tmp.put("label", bookLabel.getLabel().getLabel());
            tmp.put("value", bookLabel.getValue());
            bookLabelList.put(Integer.toString(i), tmp);
        }
        jsonBook.put("bookLabelList", bookLabelList);
        return jsonBook;
    }
}
