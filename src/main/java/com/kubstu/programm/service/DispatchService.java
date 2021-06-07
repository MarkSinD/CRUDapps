package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.repository.DispatchRepository;
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
public class DispatchService {
    public static final int DISPATCH_PER_PAGE = 100;

    @Autowired
    private DispatchRepository dispatchRepository;

    public Page<Dispatch> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, DISPATCH_PER_PAGE, sort);

        if(keyword != null){
            return dispatchRepository.findAll(keyword, pageable);
        }
        return dispatchRepository.findAll(pageable);
    }

    public Dispatch save(Dispatch administration) {
        return dispatchRepository.save(administration);
    }


    public boolean isNameUnique(Integer id, String name){
        Dispatch administrationByName = dispatchRepository.getDispatchByEmail(id);

        if( id != null && administrationByName == null) {
            return true;
        }
        else if( id != null && administrationByName != null) {
            if(!administrationByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && administrationByName != null) {
            return false;
        }
        else if(id == null && administrationByName == null) {
            return true;
        }
        return false;
    }

    public Dispatch getAdministrationById(Integer id) throws DippatchNotFoundException {
        try {
            return dispatchRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new DippatchNotFoundException("Could not find any administration with ID");
        }
    }

    public List<Dispatch> getAdministrations(){
        return (List<Dispatch>) dispatchRepository.findAll();
    }

    public void delete(Integer id) throws DippatchNotFoundException {
        Long countById = dispatchRepository.countById(id);
        if(countById == null || countById == 0){
            throw new DippatchNotFoundException("Could not find any administration with ID" + id);
        }
        dispatchRepository.deleteById(id);
    }

}
