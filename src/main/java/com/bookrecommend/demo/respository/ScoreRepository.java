package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query(value = "select count(s) from Score s where s.bookId = :bookId")
    Integer findScoreNumberByBookId(@Param("bookId") Integer bookId);

    @Query(value = "select count(s) from Score s where s.bookId = :bookId and s.score = :score")
    Integer findScoreNumberByBookIdAndScore(@Param("bookId") Integer bookId, @Param("score") Integer score);

    Score findScoreByUserIdAndBookId(Integer userId, Integer bookId);
}
