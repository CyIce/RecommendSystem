package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select count(c) from User u,Collection c " +
            "where u.id = c.userId and c.bookId = :bookId and u.id = :userId")
    Integer isCollectedByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);


    @Query(value = "select count(w) from User u,Want w " +
            "where u.id = w.userId and w.bookId = :bookId and u.id = :userId")
    Integer isWantByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    @Query(value = "select count(r) from User u,ReadingRecord r " +
            "where u.id = r.userId and r.bookId = :bookId and u.id = :userId")
    Integer isReadingByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    @Query(value = "select count(h) from User u,HaveRead h " +
            "where u.id = h.userId and h.bookId = :bookId and u.id = :userId")
    Integer isHaveReadByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    @Query(value = "select count(s) from User u,ShopingCart s " +
            "where u.id = s.userId and s.bookId = :bookId and u.id = :userId")
    Integer inShopingCartByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
