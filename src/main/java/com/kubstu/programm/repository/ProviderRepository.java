package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProviderRepository extends PagingAndSortingRepository<Provider, Integer> {
    @Query("SELECT o FROM Provider o WHERE o.title = :title")
    Provider getProviderByName(@Param("title") String title);

    Long countById(Integer id);

    @Query("SELECT o FROM Provider o WHERE CONCAT(o.id, ' ', o.title, ' ', o.address, ' ', o.telephone, ' ', o.email) LIKE %?1%")
    Page<Provider> findAll(String keyword, Pageable pageable);
}
