package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Press;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PressRepository extends JpaRepository<Press, Integer> {
    Press findPressById(Integer pressId);

    Press findPressByName(String name);
}
