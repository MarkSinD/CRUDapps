package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends PagingAndSortingRepository<Information, Integer> {
    @Query("SELECT o FROM Information o WHERE o.name = :name")
    Information getInformationByName(@Param("name") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Information o WHERE CONCAT(o.id, ' ', o.surname, ' ', o.name, ' ', o.patronymic, ' ', o.gender, ' ', o.passport, ' ', o.address, ' ', o.telephone) LIKE %?1%")
    Page<Information> findAll(String keyword, Pageable pageable);

}
