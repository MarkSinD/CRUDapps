package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Order getOrderByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Order o")
    Page<Order> findAll(@Param("keyword") String keyword, Pageable pageable);
}
