package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Operation;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.OperationNotFoundException;
import com.kubstu.programm.repository.OperationRepository;
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
public class OperationService {
    public static final int OPERATION_PER_PAGE = 100;

    @Autowired
    private OperationRepository operationRepository;

    public Page<Operation> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, OPERATION_PER_PAGE, sort);

        if(keyword != null){
            return operationRepository.findAll(keyword, pageable);
        }
        return operationRepository.findAll(pageable);
    }

    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }


    public boolean isNameUnique(Integer id, String name){
        Operation operationByEmail = operationRepository.getOperationByEmail(id);

        if( id != null && operationByEmail == null) {
            return true;
        }
        else if( id != null && operationByEmail != null) {
            if(!operationByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && operationByEmail != null) {
            return false;
        }
        else if(id == null && operationByEmail == null) {
            return true;
        }
        return false;
    }

    public Operation getOperationById(Integer id) throws OperationNotFoundException {
        try {
            return operationRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new OperationNotFoundException("Could not find any operation with ID");
        }
    }

    public List<Operation> getOperations(){
        return (List<Operation>) operationRepository.findAll();
    }

    public void delete(Integer id) throws OperationNotFoundException {
        Long countById = operationRepository.countById(id);
        if(countById == null || countById == 0){
            throw new OperationNotFoundException("Could not find any operation with ID" + id);
        }
        operationRepository.deleteById(id);
    }

}