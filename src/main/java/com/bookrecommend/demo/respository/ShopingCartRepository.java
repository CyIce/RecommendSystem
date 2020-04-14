package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.ShopingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopingCartRepository extends JpaRepository<ShopingCart, Integer> {

    // 根据用户id获取用户的购物车列表
    Page<ShopingCart> findShopingCartsByUserId(Pageable pageable, Integer userId);

    // 根据用户id和书籍id获取购物车信息
    ShopingCart findShopingCartByUserIdAndBookId(Integer userId, Integer bookId);

    boolean existsShopingCartByUserIdAndBookId(Integer userId, Integer bookId);
}
