package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface HouseRepository extends PagingAndSortingRepository<House, Integer> {
    @Query("SELECT o FROM House o WHERE o.address = :address")
    House getHouseByName(@Param("address") String address);

    Long countById(Integer id);

    @Query("SELECT o FROM House o WHERE CONCAT(o.id, ' ', o.address, ' ', o.countApartments) LIKE %?1%")
    Page<House> findAll(String keyword, Pageable pageable);
}
