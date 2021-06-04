package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.ServicePerformed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ServicePerformedRepository extends PagingAndSortingRepository<ServicePerformed, Integer> {
    @Query("SELECT o FROM ServicePerformed o WHERE o.id = :id")
    ServicePerformed getServicePerformedByName(@Param("id") String id);

    Long countById(Integer id);

    @Query("SELECT o FROM ServicePerformed o WHERE CONCAT(o.id, ' ', o.dateService) LIKE %?1%")
    Page<ServicePerformed> findAll(String keyword, Pageable pageable);
}
