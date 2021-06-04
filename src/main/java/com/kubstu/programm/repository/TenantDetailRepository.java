package com.kubstu.programm.repository;

import com.kubstu.programm.entity.TenantDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TenantDetailRepository extends PagingAndSortingRepository<TenantDetail, Integer> {
    @Query("SELECT o FROM TenantDetail o WHERE CONCAT(o.id, '') LIKE %?1%")
    TenantDetail getTenantDetailByName(@Param("name") String id);

    Long countById(Integer id);
    @Query("SELECT o FROM TenantDetail o")
    Page<TenantDetail> findAll(String keyword, Pageable pageable);
}
