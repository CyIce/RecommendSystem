package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.BookLabelOnly;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.entity.Book;
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
}
