package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {
}
