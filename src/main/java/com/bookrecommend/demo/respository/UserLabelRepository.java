package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.UserLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLabelRepository extends JpaRepository<UserLabel, Integer> {
}
