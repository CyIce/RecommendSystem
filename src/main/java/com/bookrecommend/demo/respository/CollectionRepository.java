package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    // 根据用户id和书籍id获取收藏
    Collection findCollectionByUserIdAndBookId(Integer userId, Integer booId);


}
