package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Serve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public interface ServeRepository extends PagingAndSortingRepository<Serve, Integer> {
    @Query("SELECT o FROM Serve o WHERE o.title = :title")
    Serve getServeByName(@Param("title") String title);

    Long countById(Integer id);

    @Query("SELECT o FROM Serve o WHERE CONCAT(o.id, ' ', o.title, ' ', o.price) LIKE %?1%")
    Page<Serve> findAll(String keyword, Pageable pageable);
}
