package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
