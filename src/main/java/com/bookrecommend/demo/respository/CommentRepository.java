package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

//    Page<Comment> findCommentsByBookId(Pageable pageable, Integer bookId);
//
//    Page<Comment> findCommentsByUserId(Pageable pageable, Integer userId);

    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(c.userId,u.name,u.photo,c.comment,c.score,c.hot,c.date) " +
            "from Comment c,User u " +
            "where c.bookId = :bookId and c.userId = u.id")
    Page<CommentOnly> findCommentsByBookId(Pageable pageable, @Param("bookId") Integer bookId);

    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(b.nameCn,b.picture,c.comment,c.score,c.date) " +
            "from Comment c,User u,Book b where " +
            "u.id = c.userId and c.bookId = b.id and u.id = :userId ")
    Page<CommentOnly> findCommentsByUserId(Pageable pageable, @Param("userId") Integer userId);
}
