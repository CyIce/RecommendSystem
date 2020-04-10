package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findBookById(Integer bookId);
}
