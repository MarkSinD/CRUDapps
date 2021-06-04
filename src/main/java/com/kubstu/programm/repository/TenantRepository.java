package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.ServicePerformed;
import com.kubstu.programm.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TenantRepository extends PagingAndSortingRepository<Tenant, Integer> {
    @Query("SELECT o FROM Tenant o WHERE o.name = :name")
    Tenant getTenantByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Tenant o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname) LIKE %?1%")
    Page<Tenant> findAll(String keyword, Pageable pageable);
}
