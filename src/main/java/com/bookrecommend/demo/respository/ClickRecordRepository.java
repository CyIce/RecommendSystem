package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.ClickRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRecordRepository extends JpaRepository<ClickRecord, Integer> {
}
