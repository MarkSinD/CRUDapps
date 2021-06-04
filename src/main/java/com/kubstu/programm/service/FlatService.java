package com.kubstu.programm.service;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.FlatNotFoundException;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.repository.FlatRepository;
import com.kubstu.programm.repository.OrganizationRepository;
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
public class FlatService {
    public static final int FLAT_PER_PAGE = 4;

    @Autowired
    private FlatRepository flatRepository;

    public Page<Flat> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, FLAT_PER_PAGE, sort);

        if(keyword != null){
            return flatRepository.findAll(keyword, pageable);
        }
        return flatRepository.findAll(pageable);
    }

    public Flat save(Flat flat) {
        return flatRepository.save(flat);
    }

    public boolean isNameUnique(Integer id, String name){
        Flat flatByName = flatRepository.getFlatByName(name);
        if( id != null && flatByName == null) {
            return true;
        }
        else if( id != null && flatByName != null) {
            if(!flatByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && flatByName != null) {
            return false;
        }
        else if(id == null && flatByName == null) {
            return true;
        }
        return false;
    }

    public Flat getFlatById(Integer id) throws FlatNotFoundException {
        try {
            return flatRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new FlatNotFoundException("Could not find any flat with ID");
        }
    }

    public void delete(Integer id) throws FlatNotFoundException {
        Long countById = flatRepository.countById(id);
        if(countById == null || countById == 0){
            throw new FlatNotFoundException("Could not find any flat with ID" + id);
        }
        flatRepository.deleteById(id);
    }

    public List<Flat> getFlats(){
        return (List<Flat>) flatRepository.findAll();
    }
}
