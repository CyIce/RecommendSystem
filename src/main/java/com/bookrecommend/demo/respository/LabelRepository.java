package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Integer> {
}
