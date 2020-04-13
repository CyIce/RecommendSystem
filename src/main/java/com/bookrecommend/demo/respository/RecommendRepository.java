package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Recommend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

    @Query(value = "select distinct r from Recommend r where r.userId = :userId")
    Page<Recommend> findRecommendsByUserId(Pageable pageable, @Param("userId") Integer userId);
}
