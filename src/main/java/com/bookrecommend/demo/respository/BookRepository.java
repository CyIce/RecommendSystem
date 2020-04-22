package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.BookLabelOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.entity.Book;
import com.bookrecommend.demo.entity.Kind;
import com.bookrecommend.demo.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

//    Book findBookById(Integer bookId);

//    // 根据关键字搜索书籍
//    @Query(value = "select distinct b from Book b,BookLabel label where " +
//            "(b.nameCn like %:keyword% or b.nameEng like %:keyword%) " +
//            "and (label.bookId = b.id) and (label.labelId in (:labelIdList))")
//    Page<Book> searchByKeyword(Pageable pageable, @Param("keyword") String keyword, @Param("labelIdList") List<Integer> labelIdList);


    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.bigPicture) " +
            "from Book b order by b.weekHot desc ")
    List<BookOnly> findBooksByMonthHot();

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.nameEng,b.picture) " +
            "from Book b,Recommend r " +
            "where b.id = r.bookId " +
            "and r.userId = :userId " +
            "order by r.value desc ")
    List<BookOnly> findRecommendBooksByUserId(@Param("userId") Integer userId);


    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.wordCount,b.score) " +
            "from Book b " +
            "order by b.hot")
    List<BookOnly> findHotBooks();

    @Query(value = "select new com.bookrecommend.demo.Data.BookLabelOnly(b.id,k.kind) " +
            "from BookKind b,Kind k " +
            "where b.kindId = k.id and b.bookId = :bookId " +
            "order by b.value desc ")
    List<BookLabelOnly> findBookKindsByBookId(@Param("bookId") Integer bookId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookLabelOnly(b.id,l.label) " +
            "from BookLabel b,Label l " +
            "where b.labelId = l.id and b.bookId = :bookId " +
            "order by b.value desc ")
    List<BookLabelOnly> findBookLabelsByBookId(@Param("bookId") Integer bookId);

    @Query(value = "select distinct new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.catalog,b.publicationDate,b.wordCount,b.price,b.score,b.introduction,p.name) " +
            "from Book b, Press p " +
            "where b.pressId = p.id " +
            "and b.id = :bookId")
    BookOnly findBookByBookId(@Param("bookId") Integer bookId);


    @Query(value = "select k from Kind k order by hot desc ")
    List<Kind> findAllKind();

    @Query(value = "select l from Label l order by hot desc ")
    List<Label> findAllLabel();


    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.score,b.introduction) " +
            "from Book b")
    Page<BookOnly> findAllBook(Pageable pageable);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.score,b.introduction) " +
            "from Book b,BookKind k,BookLabel l where " +
            "b.id = k.bookId and k.kindId = :kindId " +
            "and b.id = l.bookId and l.labelId = :labelId")
    Page<BookOnly> findAllBookByKindIdAndLabelId(Pageable pageable, @Param("kindId") Integer kindId, @Param("labelId") Integer labelId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.score,b.introduction) " +
            "from Book b,BookKind k where " +
            "b.id = k.bookId and k.kindId = :kindId")
    Page<BookOnly> findAllBookByKindId(Pageable pageable, @Param("kindId") Integer kindId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.score,b.introduction) " +
            "from Book b,BookLabel l where " +
            "b.id = l.bookId and l.labelId = :labelId")
    Page<BookOnly> findAllBookByLabelId(Pageable pageable, @Param("labelId") Integer labelId);


    // 获取用户在读的书籍
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.nameEng,b.picture) " +
            "from Book b,ReadingRecord r " +
            "where b.id = r.bookId and r.userId = :userId " +
            "order by r.date desc ")
    Page<BookOnly> findReadingBooksByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    // 获取用户想读的书籍
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.nameEng,b.picture) " +
            "from Book b,Want w " +
            "where b.id = w.bookId and w.userId = :userId " +
            "order by w.date desc ")
    Page<BookOnly> findWantBooksByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    // 获取用户已读的书籍
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.nameEng,b.picture) " +
            "from Book b,HaveRead h " +
            "where b.id = h.bookId and h.userId = :userId " +
            "order by h.date desc ")
    Page<BookOnly> findHaveReadBooksByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    // 想看
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,w.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,Want w " +
            "where b.pressId = p.id and b.id = w.bookId and w.userId = :userId " +
            "order by w.date desc ")
    Page<BookOnly> findWantBooksAllInfoByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,w.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,Want w " +
            "where b.pressId = p.id and b.id = w.bookId and w.userId = :userId ")
    Page<BookOnly> findWantBooksAllInfoByUserId(Pageable pageable, @Param("userId") Integer userId);

    // 在看
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,r.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,ReadingRecord r " +
            "where b.pressId = p.id and b.id = r.bookId and r.userId = :userId " +
            "order by r.date desc ")
    Page<BookOnly> findReadingBooksAllInfoByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,r.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,ReadingRecord r " +
            "where b.pressId = p.id and b.id = r.bookId and r.userId = :userId ")
    Page<BookOnly> findReadingBooksAllInfoByUserId(Pageable pageable, @Param("userId") Integer userId);

    // 看过
    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,h.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,HaveRead h " +
            "where b.pressId = p.id and b.id = h.bookId and h.userId = :userId " +
            "order by h.date desc ")
    Page<BookOnly> findHaveReadBooksAllInfoByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);

    @Query(value = "select new com.bookrecommend.demo.Data.BookOnly(b.id,b.nameCn,b.picture,b.publicationDate,h.date,b.price,b.score,p.name,b.wordCount) " +
            "from Book b,Press p,HaveRead h  " +
            "where b.pressId = p.id and b.id = h.bookId and h.userId = :userId ")
    Page<BookOnly> findHaveReadBooksAllInfoByUserId(Pageable pageable, @Param("userId") Integer userId);

}
