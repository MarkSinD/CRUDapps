package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Subscription;
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
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Integer> {
    @Query("SELECT o FROM Subscription o WHERE o.subscriptionDate = :subscriptionDate")
    Subscription getSubscriptionByEmail(@Param("subscriptionDate") String subscriptionDate);

    Long countById(Integer id);

    @Query("SELECT o FROM Subscription o WHERE CONCAT(o.id, ' ', o.subscriptionDate, ' ', o.subscriptionTerm) LIKE %?1%")
    Page<Subscription> findAll(String keyword, Pageable pageable);
}
