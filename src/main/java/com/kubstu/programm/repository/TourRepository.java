package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Employee;
import com.kubstu.programm.entity.Tour;
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
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {

    @Query("SELECT o FROM Employee o WHERE o.id = :id")
    Tour getTourByEmail(@Param("id") Integer id);

    Long countById(Integer id);

    @Query("SELECT o FROM Tour o WHERE CONCAT(o.id, ' ', o.country, ' ', o.resort, ' ', o.transport, ' ', o.accommodationType, ' ', o.powerType, ' ', o.arrivalDate, ' ', o.departureDate, ' ', o.tour) LIKE %?1%")
    Page<Tour> findAll(String keyword, Pageable pageable);
}
