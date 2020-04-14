package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    // 根据用户id查询用户收藏列表
    Page<Collection> findCollectionsByUserId(Pageable pageable, Integer userId);

    // 根据用户id和书籍id判读用户是否已经收藏此书籍
    Collection findCollectionByUserIdAndBookId(Integer userId, Integer booId);
}
