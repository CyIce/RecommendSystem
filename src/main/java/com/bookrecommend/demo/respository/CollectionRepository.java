package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

//    // 根据用户id查询用户收藏列表
//    Page<Collection> findCollectionsByUserId(Pageable pageable, Integer userId);
//
//    // 根据用户id和书籍id获取收藏
//    Collection findCollectionByUserIdAndBookId(Integer userId, Integer booId);
//
//    // 根据用户id和书籍id判断书籍是否被用户收藏
//    boolean existsCollectionByUserIdAndBookId(Integer userId, Integer bookId);
}
