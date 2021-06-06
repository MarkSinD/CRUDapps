package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Land;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository extends PagingAndSortingRepository<Land, Integer> {
    @Query("SELECT o FROM Land o WHERE o.address = :address")
    Land getLandByAddress(@Param("address") String address);

    Long countById(Integer id);

    @Query("SELECT o FROM Land o WHERE CONCAT(o.id, ' ', o.address, ' ', o.size, ' ', o.affiliation, ' ', o.notes) LIKE %?1%")
    Page<Land> findAll(String keyword, Pageable pageable);
}
