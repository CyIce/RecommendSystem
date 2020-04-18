package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.AuthorOnly;
import com.bookrecommend.demo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "select new com.bookrecommend.demo.Data.AuthorOnly(a.id,a.nameCn) " +
            "from Author a,AuthorToBook ab " +
            "where a.id = ab.authorId " +
            "and ab.bookId = :bookId")
    List<AuthorOnly> findAuthorsByBookId(@Param("bookId") Integer bookId);
}
