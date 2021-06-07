package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Subscriber;
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
public interface SubscriberRepository extends PagingAndSortingRepository<Subscriber, Integer> {
    @Query("SELECT o FROM Subscriber o WHERE o.address = :address")
    Subscriber getSubscriberByEmail(@Param("address") String address);

    Long countById(Integer id);

    @Query("SELECT o FROM Subscriber o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname, ' ', o.patronymic, ' ', o.address) LIKE %?1%")
    Page<Subscriber> findAll(String keyword, Pageable pageable);
}
