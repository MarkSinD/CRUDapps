package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Dispatch;
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
public interface DispatchRepository extends PagingAndSortingRepository<Dispatch, Integer> {
    @Query("SELECT o FROM Dispatch o WHERE o.id = :id")
    Dispatch getDispatchByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Dispatch o WHERE CONCAT(o.id, ' ', o.sendingType, ' ', o.senderDate, ' ', o.recipientData, ' ', o.weight, ' ', o.value) LIKE %?1%")
    Page<Dispatch> findAll(String keyword, Pageable pageable);
}
