package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findCommentsByBookId(Pageable pageable, Integer bookId);

    Page<Comment> findCommentsByUserId(Pageable pageable, Integer userId);
}
