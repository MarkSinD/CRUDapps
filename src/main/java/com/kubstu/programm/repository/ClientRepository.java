package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
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
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {

    @Query("SELECT o FROM Client o WHERE o.telephone = :telephone")
    Client getClientByEmail(@Param("telephone") Integer telephone);

    Long countById(Integer id);

    @Query("SELECT o FROM Client o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname, ' ', o.patronymic, ' ', o.address, ' ', o.telephone) LIKE %?1%")
    Page<Client> findAll(String keyword, Pageable pageable);
}
