package com.bookrecommend.demo.respository;

import com.bookrecommend.demo.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    @Query(value = "select distinct c from Collection c where c.userId = :userId")
    Page<Collection> findCollectionsByUserId(Pageable pageable, @Param("userId") Integer userId);
}
