//package com.bookrecommend.demo.respository;
//
//import com.bookrecommend.demo.entity.ReadingRecord;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Integer> {
//
//    // 根据用户id获取用户的阅读记录
//    Page<ReadingRecord> findReadingRecordsByUserId(Pageable pageable, Integer userId);
//
//    // 根据用户id和书籍id获取书籍阅读记录
//    ReadingRecord findReadingRecordByUserIdAndBookId(Integer userId, Integer bookId);
//
//    // 根据用户id和书籍id判断书籍是否被用户阅读
//    boolean existsReadingRecordByUserIdAndBookId(Integer userId, Integer bookId);
//
//}
