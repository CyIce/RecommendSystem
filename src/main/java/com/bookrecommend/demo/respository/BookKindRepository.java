package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.BookKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookKindRepository extends JpaRepository<BookKind, Integer> {
}
