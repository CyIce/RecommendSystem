package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.ReadingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Integer> {

    //    @Query(value = "select distinct r from ReadingRecord r where r.userId = :userId")
    Page<ReadingRecord> findReadingRecordsByUserId(Pageable pageable, @Param("userId") Integer userId);

}
