package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void addList(){
        Employee employee1 = new Employee();
        employee1.setName("Vladimir");
        employee1.setSurname("Gorbunov");
        employee1.setPatronymic("Mikhailovich");
        Date date = Date.valueOf("1992-08-16");
        employee1.setDateBirth(date);
        employee1.setPosition("CEO");
        employee1.setAddress("Rossiyskaya, 257, Krasnodar");

        Employee employee2 = new Employee();
        employee2.setName("Alexander");
        employee2.setSurname("Kaleri");
        employee2.setPatronymic("Yurievich");
        date = Date.valueOf("1992-08-11");
        employee2.setDateBirth(date);
        employee2.setPosition("Executive Director");
        employee2.setAddress("Dzerzhinsky, 42, Krasnodar");

        Employee employee3 = new Employee();
        employee3.setName("Anatoly");
        employee3.setSurname("Kvochur");
        employee3.setPatronymic("Nikolaevich");
        date = Date.valueOf("1992-10-17");
        employee3.setDateBirth(date);
        employee3.setPosition("CFO");
        employee3.setAddress("Western bypass, 29, Krasnodar");

        Employee employee4 = new Employee();
        employee4.setName("Sergey");
        employee4.setSurname("Krikalev");
        employee4.setPatronymic("Konstantinovich");
        date = Date.valueOf("1992-04-11");
        employee4.setDateBirth(date);
        employee4.setPosition("Commercial Director");
        employee4.setAddress("Eastern bypass, 19, Krasnodar");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);

        Iterable<Employee> employees1 = employeeRepository.saveAll(employees);
        assertThat(employees1.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }

    @Test
    public void addOne(){
        Employee employee = new Employee();
        employee.setName("Vladimir");
        employee.setSurname("Gorbunov");
        employee.setPatronymic("Mikhailovich");
        Date date = Date.valueOf("1992-08-16");
        employee.setDateBirth(date);
        employee.setPosition("Director of Human Resources");
        employee.setAddress("Solnechnaya, 18/3, Krasnodar");
        employeeRepository.save(employee);
    }


}
