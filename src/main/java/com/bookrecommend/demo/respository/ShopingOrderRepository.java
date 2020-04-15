package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.ShopingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface ShopingOrderRepository extends JpaRepository<ShopingOrder, Integer> {

    @Modifying
    @Transactional
    void deleteShopingOrdersByUserOrderId(Integer userOrderId);
}
