package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Employee;
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
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {

    @Query("SELECT o FROM Employee o WHERE o.address = :address")
    Employee getEmployeeByEmail(@Param("address") String address);

    Long countById(Integer id);

    @Query("SELECT o FROM Employee o WHERE CONCAT(o.id, ' ', o.name, ' ', o.surname, ' ', o.patronymic, ' ', o.dateBirth, ' ', o.position, ' ', o.address) LIKE %?1%")
    Page<Employee> findAll(String keyword, Pageable pageable);
}
