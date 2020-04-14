package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    // 根据用户id查询用户收藏列表
    @Query(value = "select distinct c from Collection c where c.userId = :userId")
    Page<Collection> findCollectionsByUserId(Pageable pageable, @Param("userId") Integer userId);

    // 根据用户id和书籍id判读用户是否已经收藏此书籍
    @Query(value = "select c from Collection c where c.userId = :userId and c.bookId = :bookId")
    Collection findCollectionsByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer booId);
}
