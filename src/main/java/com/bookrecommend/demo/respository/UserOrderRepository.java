package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    Page<UserOrder> findUserOrdersByUserId(Pageable pageable, Integer userId);
}
