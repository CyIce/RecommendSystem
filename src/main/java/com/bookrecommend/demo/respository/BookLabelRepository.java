package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.BookLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLabelRepository extends JpaRepository<BookLabel, Integer> {
}
