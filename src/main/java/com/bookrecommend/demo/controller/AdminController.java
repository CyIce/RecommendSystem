package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.entity.*;
import com.bookrecommend.demo.respository.*;
import com.bookrecommend.demo.util.SlopeOne;
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

import java.util.*;

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

    @Autowired
    private PressRepository pressRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private CommentRepository commentRepository;

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
            AuthorToBook authorToBook = new AuthorToBook(id, book.getId());
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

    @GetMapping(value = "/admin/writedata")
    @ResponseBody
    public String writedata() {
        JSONArray books = JSONObject.parseArray(Utils.getJson());
        Integer userId = 1;
        String bigPicture = "https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/880a18546e1cf5ff7db425add24a048d.jpg";
        String catalog = "中文版自序\n" +
                "韩文版自序\n" +
                "日文版自序\n" +
                "英文版自序\n" +
                "麦田新版自序\n" +
                "活着\n" +
                "外文版评论摘要";

        for (int i = 0; i < books.size(); i++) {
            JSONObject bookJson = books.getJSONObject(i);
            Book book = new Book();
            book.setNameCn(bookJson.getString("name"));
            book.setPicture(bookJson.getString("picture"));
            book.setBigPicture(bigPicture);
            book.setCatalog(catalog);
            book.setPublicationDate(new Date());
            book.setWordCount(300000);
            book.setPrice(20);
            book.setScore(bookJson.getFloat("score"));
            book.setIntroduction(bookJson.getString("introduction"));
            book.setHot(bookJson.getInteger("hot"));
            book.setWeekHot(bookJson.getInteger("hot") / 10);
            book.setMonthHot(bookJson.getInteger("hot") / 4);
            book.setCollectionNum(0);
            book.setWantNum(0);
            book.setReadingNum(0);
            book.setHaveReadNum(0);
            book.setCreateTime(new Date());
            book.setModifiedTime(new Date());

            String pressName = bookJson.getString("press");
            Press press = pressRepository.findPressByName(pressName);
            if (press == null) {
                press = new Press(pressName, "无", new Date());
                pressRepository.save(press);
            }
            book.setPressId(press.getId());
            bookRepository.save(book);

            String authorName = bookJson.getString("author");
            Author author = authorRepository.findAuthorByNameCn(authorName);
            if (author == null) {
                author = new Author(authorName, "无", bookJson.getString("introduction"), bookJson.getFloat("score"));
                authorRepository.save(author);
            }
            AuthorToBook ab = new AuthorToBook(author.getId(), book.getId());
            authorToBookRepository.save(ab);

            String[] comments = bookJson.getString("comment").split("\n");
            for (String comment : comments) {
                Comment c = new Comment(book.getId(), userId, comment, 0, 8, new Date());
            }

            double d1 = Math.random();
            double d2 = Math.random();
            double d = Math.random();
            int kindId = (int) (d1 * 20) + 1;
            int labelId = (int) (d2 * 20) + 1;
            int value = (int) (d * 100);

            BookKind bookKind = new BookKind(kindId, book.getId(), value);
            bookKindRepository.save(bookKind);
            BookLabel bookLabel = new BookLabel(labelId, book.getId(), value);
            bookLabelRepository.save(bookLabel);

        }


        return "1";
    }

    @GetMapping(value = "/admin/downloadimg")
    @ResponseBody
    public String downloadImg() throws Exception {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            if (book.getId() >= 70) {
                Utils.download(book.getPicture(), book.getId());
            }
        }


        return "1";

    }

    @GetMapping(value = "/admin/recommend")
    @ResponseBody
    public String recommned() {
        Integer k = 10;
        Map<Integer, Map<Integer, Integer>> userRations = new HashMap<Integer, Map<Integer, Integer>>();
        Map<Integer, Integer> userScores = new HashMap<Integer, Integer>();
        List<Integer> usersId = new ArrayList<Integer>();
        List<Score> scores = scoreRepository.findAll();
        Integer nowUserId = -1;
        for (Score score : scores) {
            if (score.getUserId() != nowUserId) {
                if (userScores.size() > 0) {
                    userRations.put(nowUserId, userScores);
                    usersId.add(nowUserId);
                    userScores = new HashMap<Integer, Integer>();
                }
                nowUserId = score.getUserId();
            }
            userScores.put(score.getBookId(), score.getScore());
        }
        userRations.put(nowUserId, userScores);
        SlopeOne slopeOne = new SlopeOne(userRations);
        slopeOne.computeDeviation();

        recommendRepository.deleteAll();
        for (Integer userId : usersId) {
            List<Map.Entry<Integer, Double>> topK = slopeOne.predictRating(userRations.get(userId), k);
            for (Map.Entry<Integer, Double> item : topK) {
                Recommend r = new Recommend(item.getKey(), userId, item.getValue(), new Date());
                recommendRepository.save(r);
            }
        }


        return "1";
    }
}
