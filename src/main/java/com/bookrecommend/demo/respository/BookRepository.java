package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findBookById(Integer bookId);

    // 根据关键字搜索书籍
    @Query(value = "select distinct b from Book b,BookLabel label where " +
            "(b.nameCn like %:keyword% or b.nameEng like %:keyword%) and (label.bookId = b.id) and (label.labelId in (:label_list))")
//    Page<Book> searchByKeyword(Pageable pageable, @Param("keyword") String keyword);
    Page<Book> searchByKeyword(Pageable pageable, @Param("keyword") String keyword, @Param("label_list") List<Integer> labelIdList);

}
