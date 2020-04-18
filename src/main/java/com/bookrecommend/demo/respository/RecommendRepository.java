package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

//    @Query(value = "select distinct r from Recommend r where r.userId = :userId")
//    Page<Recommend> findRecommendsByUserId(Pageable pageable, @Param("userId") Integer userId);

}
