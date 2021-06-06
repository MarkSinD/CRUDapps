package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends PagingAndSortingRepository<Enterprise, Integer> {
    @Query("SELECT o FROM Enterprise o WHERE o.name = :name")
    Enterprise getEnterpriseByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Enterprise o WHERE CONCAT(o.id, ' ', o.name, ' ', o.address, ' ', o.telephone) LIKE %?1%")
    Page<Enterprise> findAll(String keyword, Pageable pageable);
}
