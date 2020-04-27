package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.entity.*;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorToBookRepository authorToBookRepository;

    @Autowired
    private BookKindRepository bookKindRepository;

    @Autowired
    private BookLabelRepository bookLabelRepository;

    @GetMapping(value = "/admin/user")
    public String getUser(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam("order") String order,
                          Model model) {
        log.info("用户管理");

        offset -= 1;
        Sort sort = Sort.by(Sort.Order.asc(order));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<UserOnly> users = userRepository.findAllUser(pageable);

        model.addAttribute("users", users.toList());
        model.addAttribute("order", order);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("userNumber", users.getTotalElements());

        return "admin_user";
    }

    @GetMapping(value = "/admin/book")
    public String getBook(@RequestParam("offset") Integer offset,
                          @RequestParam("limit") Integer limit,
                          @RequestParam("order") String order,
                          Model model) {
        log.info("书籍管理");

        offset -= 1;
        Sort sort = Sort.by(Sort.Order.asc(order));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<BookOnly> booksJson = bookRepository.findAllBooks(pageable);

        List<BookOnly> books = booksJson.toList();
        for (BookOnly book : books) {
            book.setKinds(bookRepository.findBookKindsByBookId(book.getId()));
            book.setLabels(bookRepository.findBookLabelsByBookId(book.getId()));
            book.setAuthors(authorRepository.findAuthorsInfoByBookId(book.getId()));

        }

        model.addAttribute("books", booksJson.toList());
        model.addAttribute("order", order);
        model.addAttribute("currentPage", offset + 1);
        model.addAttribute("totalPages", booksJson.getTotalPages());
        model.addAttribute("userNumber", booksJson.getTotalElements());

        return "admin_book";
    }

    @GetMapping(value = "/admin/addbook")
    public String addBookView() {

        return "add_book";
    }

    @PostMapping(value = "/admin/addbook")
    @ResponseBody
    public String addBook(@RequestParam("book_name") String bookName,
                          @RequestParam("big_picture") String bigPicture,
                          @RequestParam("picture") String picture,
                          @RequestParam("introduction") String introduction,
                          @RequestParam("authors_id") String authorsId,
                          @RequestParam("kind_id") String kindId,
                          @RequestParam("label_id") String labelId,
                          @RequestParam("catalog") String catalog,
                          @RequestParam("press_id") Integer pressId,
                          @RequestParam("price") String price,
                          @RequestParam("publication_date") String publicationDate,
                          @RequestParam("word_count") Integer wordCount,
                          @RequestParam("score") Integer score,
                          @RequestParam("hot") Integer hot) {

        log.info("添加书籍");

        Book book = new Book();
        book.setNameCn(bookName);
        book.setBigPicture(bigPicture);
        book.setPicture(picture);
        book.setIntroduction(introduction);
        book.setCatalog(catalog);
        book.setPressId(pressId);
        book.setPrice(Integer.parseInt(price));
        book.setScore(score);
        book.setPublicationDate(Utils.String2Date(publicationDate, true));
        book.setWordCount(wordCount);
        book.setHot(hot);
        book.setMonthHot(hot);
        book.setWeekHot(hot);
        book.setCreateTime(new Date());
        book.setModifiedTime(new Date());
        book.setCollectionNum(0);
        book.setReadingNum(0);
        book.setWantNum(0);
        book.setHaveReadNum(0);

        bookRepository.save(book);

        String[] authorIdList = authorsId.split(" ");
        for (String s : authorIdList) {
            int id = Integer.parseInt(s);
            AuthorToBook authorToBook = new AuthorToBook();
            authorToBook.setAuthorId(id);
            authorToBook.setBookId(book.getId());
            authorToBookRepository.save(authorToBook);
        }
        String[] kindIdList = kindId.split(" ");
        for (String s : kindIdList) {
            int id = Integer.parseInt(s);
            BookKind bookKind = new BookKind(id, book.getId(), 0);
            bookKindRepository.save(bookKind);
        }
        String[] labelIdList = labelId.split(" ");
        for (String s : labelIdList) {
            int id = Integer.parseInt(s);
            BookLabel bookLabel = new BookLabel(id, book.getId(), 0);
            bookLabelRepository.save(bookLabel);
        }

        return "1";
    }

    @GetMapping(value = "/admin/addauthor")
    public String addAuthorView() {

        return "add_author";
    }

    @PostMapping(value = "/admin/addauthor")
    @ResponseBody
    public String addAuthor(@RequestParam("name") String name,
                            @RequestParam("picture") String picture,
                            @RequestParam("introduction") String introduction,
                            @RequestParam("score") String score) {

        Author author = new Author(name, picture, introduction, Float.parseFloat(score));
        authorRepository.save(author);

        return "1";

    }
}
