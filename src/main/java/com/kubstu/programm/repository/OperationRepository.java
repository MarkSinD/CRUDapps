package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Operation;
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
public interface OperationRepository extends PagingAndSortingRepository<Operation, Integer> {
    @Query("SELECT o FROM Operation o WHERE o.id = :id")
    Operation getOperationByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Operation o WHERE CONCAT(o.id, ' ', o.type) LIKE %?1%")
    Page<Operation> findAll(String keyword, Pageable pageable);
}
