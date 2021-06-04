package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Serve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository  extends PagingAndSortingRepository<Serve, Integer> {
    @Query("SELECT o FROM Serve o WHERE o.name = :name")
    Serve getServeByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Serve o WHERE CONCAT(o.id, ' ', o.name, ' ', o.price) LIKE %?1%")
    Page<Serve> findAll(String keyword, Pageable pageable);
}
