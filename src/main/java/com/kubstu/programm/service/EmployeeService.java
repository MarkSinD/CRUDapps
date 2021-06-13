package com.kubstu.programm.service;

import com.kubstu.programm.entity.Employee;
import com.kubstu.programm.exception.EmployeeNotFoundException;
import com.kubstu.programm.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;



@Service
@Transactional
public class EmployeeService {
    public static final int EMPLOYEE_PER_PAGE = 100;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, EMPLOYEE_PER_PAGE, sort);

        if(keyword != null){
            return employeeRepository.findAll(keyword, pageable);
        }
        return employeeRepository.findAll(pageable);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    public boolean isNameUnique(Integer id, String name){
        Employee employeeByEmail = employeeRepository.getEmployeeByEmail(name);

        if( id != null && employeeByEmail == null) {
            return true;
        }
        else if( id != null && employeeByEmail != null) {
            if(!employeeByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && employeeByEmail != null) {
            return false;
        }
        else if(id == null && employeeByEmail == null) {
            return true;
        }
        return false;
    }

    public Employee getEmployeeById(Integer id) throws EmployeeNotFoundException {
        try {
            return employeeRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new EmployeeNotFoundException("Could not find any employee with ID");
        }
    }

    public List<Employee> getEmployees(){
        return (List<Employee>) employeeRepository.findAll();
    }

    public void delete(Integer id) throws EmployeeNotFoundException{
        Long countById = employeeRepository.countById(id);
        if(countById == null || countById == 0){
            throw new EmployeeNotFoundException("Could not find any employee with ID" + id);
        }
        employeeRepository.deleteById(id);
    }

}

