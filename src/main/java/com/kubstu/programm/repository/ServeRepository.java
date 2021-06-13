package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Serve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */

@Repository
public interface ServeRepository extends PagingAndSortingRepository<Serve, Integer> {

    @Query("SELECT o FROM Serve o WHERE o.id = :id")
    Serve getServeByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Serve o")
    Page<Serve> findAll(@Param("keyword") String keyword, Pageable pageable);
}
