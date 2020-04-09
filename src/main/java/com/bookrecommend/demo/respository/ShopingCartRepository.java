package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.ShopingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopingCartRepository extends JpaRepository<ShopingCart, Integer> {
}
