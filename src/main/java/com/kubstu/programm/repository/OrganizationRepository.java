package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Integer> {

    @Query("SELECT o FROM Organization o WHERE o.name = :name")
    Organization getOrganizationByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Organization o WHERE CONCAT(o.id, ' ', o.name, ' ', o.address, ' ', o.telephone) LIKE %?1%")
    Page<Organization> findAll(String keyword, Pageable pageable);

}
