package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GoodRepository extends PagingAndSortingRepository<Good, Integer> {
    @Query("SELECT o FROM Good o WHERE o.title = :title")
    Good getGoodByName(@Param("title") String title);

    Long countById(Integer id);

    @Query("SELECT o FROM Good o WHERE CONCAT(o.id, ' ', o.title, ' ', o.price, ' ', o.quantity) LIKE %?1%")
    Page<Good> findAll(String keyword, Pageable pageable);
}
