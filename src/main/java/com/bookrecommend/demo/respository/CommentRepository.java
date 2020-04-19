package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

//    Page<Comment> findCommentsByBookId(Pageable pageable, Integer bookId);
//
//    Page<Comment> findCommentsByUserId(Pageable pageable, Integer userId);

    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(c.userId,u.name,c.comment,c.hot,c.date) " +
            "from Comment c,User u " +
            "where c.bookId = :bookId and c.userId = u.id " +
            "order by c.hot desc ")
    List<CommentOnly> findCommentsByBookIdOrOrderByHot(@Param("bookId") Integer bookId);

    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(c.userId,u.name,c.comment,c.hot,c.date) " +
            "from Comment c,User u " +
            "where c.bookId = :bookId and c.userId = u.id " +
            "order by c.date desc ")
    List<CommentOnly> findCommentsByBookIdOrOrderByDate(@Param("bookId") Integer bookId);
}
