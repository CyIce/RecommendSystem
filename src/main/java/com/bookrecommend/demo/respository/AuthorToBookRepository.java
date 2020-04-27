package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.AuthorToBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorToBookRepository extends JpaRepository<AuthorToBook, Integer> {
}
