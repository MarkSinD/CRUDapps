package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Administration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrationRepository extends PagingAndSortingRepository<Administration, Integer> {
    @Query("SELECT o FROM Administration o WHERE o.email = :email")
    Administration getAdministrationByEmail(@Param("email") String email);

    Long countById(Integer id);

    @Query("SELECT o FROM Administration o WHERE CONCAT(o.id, ' ', o.name, ' ', o.address, ' ', o.postcode, ' ', o.email) LIKE %?1%")
    Page<Administration> findAll(String keyword, Pageable pageable);
}
