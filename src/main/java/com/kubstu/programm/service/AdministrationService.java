package com.kubstu.programm.service;

import com.kubstu.programm.entity.Administration;
import com.kubstu.programm.exception.AdministrationNotFoundException;
import com.kubstu.programm.repository.AdministrationRepository;
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
public class AdministrationService {
    public static final int ADMINISTRATION_PER_PAGE = 100;

    @Autowired
    private AdministrationRepository administrationRepository;

    public Page<Administration> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, ADMINISTRATION_PER_PAGE, sort);

        if(keyword != null){
            return administrationRepository.findAll(keyword, pageable);
        }
        return administrationRepository.findAll(pageable);
    }

    public Administration save(Administration administration) {
        return administrationRepository.save(administration);
    }


    public boolean isNameUnique(Integer id, String name){
        Administration administrationByName = administrationRepository.getAdministrationByEmail(name);

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

    public Administration getAdministrationById(Integer id) throws AdministrationNotFoundException {
        try {
            return administrationRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new AdministrationNotFoundException("Could not find any administration with ID");
        }
    }

    public List<Administration> getAdministrations(){
        return (List<Administration>) administrationRepository.findAll();
    }

    public void delete(Integer id) throws AdministrationNotFoundException{
        Long countById = administrationRepository.countById(id);
        if(countById == null || countById == 0){
            throw new AdministrationNotFoundException("Could not find any administration with ID" + id);
        }
        administrationRepository.deleteById(id);
    }

}
