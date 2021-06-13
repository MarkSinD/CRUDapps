package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Employee;
import com.kubstu.programm.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */

@Repository
public interface ProviderRepository extends PagingAndSortingRepository<Provider, Integer> {

    @Query("SELECT o FROM Provider o WHERE o.telephone = :telephone")
    Provider getProviderByEmail(@Param("telephone") Integer telephone);

    Long countById(Integer id);

    @Query("SELECT o FROM Provider o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname, ' ', o.telephone, ' ', o.address) LIKE %?1%")
    Page<Provider> findAll(String keyword, Pageable pageable);
}
