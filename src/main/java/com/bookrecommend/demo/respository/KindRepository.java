package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindRepository extends JpaRepository<Kind, Integer> {
}
