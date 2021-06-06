package com.kubstu.programm.service;

import com.kubstu.programm.entity.Executive;
import com.kubstu.programm.exception.ExecutiveNotFoundException;
import com.kubstu.programm.repository.ExecutiveRepository;
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
public class ExecutiveService {
    public static final int EXECUTIVE_PER_PAGE = 100;

    @Autowired
    private ExecutiveRepository executiveRepository;

    public Page<Executive> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, EXECUTIVE_PER_PAGE, sort);

        if(keyword != null){
            return executiveRepository.findAll(keyword, pageable);
        }
        return executiveRepository.findAll(pageable);
    }

    public Executive save(Executive executive) {
        return executiveRepository.save(executive);
    }


    public boolean isNameUnique(Integer id, String name){
        Executive executiveByName = executiveRepository.getExecutiveByName(name);

        if( id != null && executiveByName == null) {
            return true;
        }
        else if( id != null && executiveByName != null) {
            if(!executiveByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && executiveByName != null) {
            return false;
        }
        else if(id == null && executiveByName == null) {
            return true;
        }
        return false;
    }

    public Executive getExecutiveById(Integer id) throws ExecutiveNotFoundException {
        try {
            return executiveRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ExecutiveNotFoundException("Could not find any executive with ID");
        }
    }

    public List<Executive> getExecutives(){
        return (List<Executive>) executiveRepository.findAll();
    }

    public void delete(Integer id) throws ExecutiveNotFoundException{
        Long countById = executiveRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ExecutiveNotFoundException("Could not find any executive with ID" + id);
        }
        executiveRepository.deleteById(id);
    }

    public List<Executive> getExecutive() {
        return (List<Executive>) executiveRepository.findAll();
    }
}
