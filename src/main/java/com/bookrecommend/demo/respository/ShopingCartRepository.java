package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.ShopingCartOnly;
import com.bookrecommend.demo.entity.ShopingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopingCartRepository extends JpaRepository<ShopingCart, Integer> {

    // 根据用户id获取用户的购物车列表
    @Query(value = "select new com.bookrecommend.demo.Data.ShopingCartOnly(s.id,s.bookId,b.nameCn,b.price,b.picture,s.number,s.date) " +
            "from ShopingCart s,Book b " +
            "where s.userId = :userId and s.bookId = b.id " +
            "order by s.date desc ")
    List<ShopingCartOnly> findShopingCartsByUserIdOrderByDateDesc(@Param("userId") Integer userId);

    // 根据用户id和书籍id获取购物车信息
    ShopingCart findShopingCartByUserIdAndBookId(Integer userId, Integer bookId);

    boolean existsShopingCartByUserIdAndBookId(Integer userId, Integer bookId);

    ShopingCart findShopingCartByIdAndUserId(Integer id, Integer userId);
}
