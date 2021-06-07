package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Edition;
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
public interface EditionRepository extends PagingAndSortingRepository<Edition, Integer> {
    @Query("SELECT o FROM Edition o WHERE o.name = :name")
    Edition getEditionByEmail(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Edition o WHERE CONCAT(o.id, ' ', o.attributeName, ' ', o.name, ' ', o.price) LIKE %?1%")
    Page<Edition> findAll(String keyword, Pageable pageable);
}
