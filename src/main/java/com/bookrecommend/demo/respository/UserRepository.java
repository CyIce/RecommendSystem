package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.Data.CommentOnly;
import com.bookrecommend.demo.Data.ShopingOrderOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.Data.UserOrderOnly;
import com.bookrecommend.demo.entity.Collection;
import com.bookrecommend.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select c from User u,Collection c " +
            "where u.id = c.userId and c.bookId = :bookId and u.id = :userId")
    Collection isCollectedByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);


    @Query(value = "select count(s) from User u,ShopingCart s " +
            "where u.id = s.userId and s.bookId = :bookId and u.id = :userId")
    Integer inShopingCartByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);


    @Query(value = "select new com.bookrecommend.demo.Data.UserOnly(u.id,u.name,u.photo,u.introduction) " +
            "from User u " +
            "where u.id = :userId")
    UserOnly findUserBaseInfoByUserId(@Param("userId") Integer UserId);

    @Query(value = "select new com.bookrecommend.demo.Data.UserOnly(u.id,u.name,u.photo) " +
            "from User u " +
            "where u.id = :userId")
    UserOnly findUserNameAndPhotoByUserId(@Param("userId") Integer UserId);


    @Query(value = "select new com.bookrecommend.demo.Data.CommentOnly(b.nameCn,b.picture,c.comment,c.score,c.date) " +
            "from User u,Comment c,Book b where " +
            "u.id = c.userId and c.bookId = b.id and u.id = :userId " +
            "order by c.date")
    Page<CommentOnly> findCommentsByUserIdOrderByDate(Pageable pageable, @Param("userId") Integer userId);


    boolean existsUserByEmailAndPassword(String email, String password);

    @Query(value = "select new com.bookrecommend.demo.Data.UserOnly(u.id) " +
            "from User u " +
            "where u.email = :email")
    UserOnly findUserByEmail(@Param("email") String email);


    @Query(value = "select new com.bookrecommend.demo.Data.UserOrderOnly(u.id,u.createTime,u.paymentTime,u.deliverTime,u.paymentStatus,u.deliverStatus,u.receiveStatus) " +
            "from UserOrder u where u.userId = :userId order by u.createTime desc ")
    Page<UserOrderOnly> findUserOrdersByUserId(Pageable pageable, @Param("userId") Integer userId);


    @Query(value = "select new com.bookrecommend.demo.Data.ShopingOrderOnly(s.id,b.id,b.nameCn,b.picture,s.price,s.number) " +
            "from ShopingOrder s,Book b " +
            "where s.userOrderId = :orderId and s.bookId = b.id")
    List<ShopingOrderOnly> findShopingOrdersByOrderId(@Param("orderId") Integer orderId);


    @Query(value = "select new com.bookrecommend.demo.Data.UserOnly(u.id,u.name,u.age,u.gender,u.photo,u.introduction,u.phoneNumber,u.email,u.registrationDate) " +
            "from User u")
    Page<UserOnly> findAllUser(Pageable pageable);

}
