package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Receiving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;


/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@Repository
public interface ReceivingRepository extends PagingAndSortingRepository<Receiving, Integer> {
    @Query("SELECT o FROM Receiving o WHERE o.id = :id")
    Receiving getReceivingByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Receiving o WHERE CONCAT(o.id, ' ', o.typeReceipt, ' ', o.senderData, ' ', o.recipientDate, ' ', o.weight, ' ', o.value) LIKE %?1%")
    Page<Receiving> findAll(String keyword, Pageable pageable);
}
