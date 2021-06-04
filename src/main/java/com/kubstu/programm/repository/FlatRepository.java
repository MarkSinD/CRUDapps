package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface FlatRepository extends PagingAndSortingRepository<Flat, Integer> {

    @Query("SELECT o FROM Flat o WHERE o.id = :id")
    Flat getFlatByName(@Param("id") String id);

    Long countById(Integer id);

    @Query("SELECT o FROM Flat o WHERE CONCAT(o.id, ' ', o.countRooms, ' ', o.livingSpace) LIKE %?1%")
    Page<Flat> findAll(String keyword, Pageable pageable);
}
