package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Executive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutiveRepository extends PagingAndSortingRepository<Executive, Integer> {
    @Query("SELECT o FROM Executive o WHERE o.name = :name")
    Executive getExecutiveByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Executive o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname, ' ', o.patronymic, ' ', o.date, ' ', o.position, ' ', o.telephone) LIKE %?1%")
    Page<Executive> findAll(String keyword, Pageable pageable);
}
