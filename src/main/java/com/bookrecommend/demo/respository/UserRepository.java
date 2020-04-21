package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Query(value = "select new com.bookrecommend.demo.Data.UserOnly(u.id,u.name,u.photo,u.introduction) " +
            "from User u " +
            "where u.id = :userId")
    UserOnly findUserBaseInfoByUserId(@Param("userId") Integer UserId);


    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(b.nameCn,b.picture,c.comment,c.score,c.date) " +
            "from User u,Comment c,Book b where " +
            "u.id = c.userId and c.bookId = b.id and u.id = :userId " +
            "order by c.date")
    Page<CommentOnly> findCommentsByUserId(Pageable pageable, @Param("userId") Integer userId);

}
