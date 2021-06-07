package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Edition;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.EditionNotFoundException;
import com.kubstu.programm.repository.EditionRepository;
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
public class EditionService {
    public static final int EDITION_PER_PAGE = 100;

    @Autowired
    private EditionRepository editionRepository;

    public Page<Edition> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, EDITION_PER_PAGE, sort);

        if(keyword != null){
            return editionRepository.findAll(keyword, pageable);
        }
        return editionRepository.findAll(pageable);
    }

    public Edition save(Edition edition) {
        return editionRepository.save(edition);
    }


    public boolean isNameUnique(Integer id, String name){
        Edition editionByEmail = editionRepository.getEditionByEmail(name);

        if( id != null && editionByEmail == null) {
            return true;
        }
        else if( id != null && editionByEmail != null) {
            if(!editionByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && editionByEmail != null) {
            return false;
        }
        else if(id == null && editionByEmail == null) {
            return true;
        }
        return false;
    }

    public Edition getEditionById(Integer id) throws EditionNotFoundException {
        try {
            return editionRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new EditionNotFoundException("Could not find any edition with ID");
        }
    }

    public List<Edition> getEditions(){
        return (List<Edition>) editionRepository.findAll();
    }

    public void delete(Integer id) throws EditionNotFoundException {
        Long countById = editionRepository.countById(id);
        if(countById == null || countById == 0){
            throw new EditionNotFoundException("Could not find any edition with ID" + id);
        }
        editionRepository.deleteById(id);
    }

}