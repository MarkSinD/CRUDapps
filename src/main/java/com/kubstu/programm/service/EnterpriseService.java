package com.kubstu.programm.service;

import com.kubstu.programm.entity.Enterprise;
import com.kubstu.programm.exception.EnterpriseNotFoundException;
import com.kubstu.programm.repository.EnterpriseRepository;
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
public class EnterpriseService {
    public static final int ENTERPRICE_PER_PAGE = 100;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public Page<Enterprise> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, ENTERPRICE_PER_PAGE, sort);

        if(keyword != null){
            return enterpriseRepository.findAll(keyword, pageable);
        }
        return enterpriseRepository.findAll(pageable);
    }

    public Enterprise save(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }


    public boolean isNameUnique(Integer id, String name){
        Enterprise enterpriseByName = enterpriseRepository.getEnterpriseByName(name);

        if( id != null && enterpriseByName == null) {
            return true;
        }
        else if( id != null && enterpriseByName != null) {
            if(!enterpriseByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && enterpriseByName != null) {
            return false;
        }
        else if(id == null && enterpriseByName == null) {
            return true;
        }
        return false;
    }

    public Enterprise getEnterpriseById(Integer id) throws EnterpriseNotFoundException {
        try {
            return enterpriseRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new EnterpriseNotFoundException("Could not find any enterprise with ID");
        }
    }

    public List<Enterprise> getEnterprises(){
        return (List<Enterprise>) enterpriseRepository.findAll();
    }

    public void delete(Integer id) throws EnterpriseNotFoundException{
        Long countById = enterpriseRepository.countById(id);
        if(countById == null || countById == 0){
            throw new EnterpriseNotFoundException("Could not find any enterprise with ID" + id);
        }
        enterpriseRepository.deleteById(id);
    }

    public List<Enterprise> getEnterprise() {
        return (List<Enterprise>) enterpriseRepository.findAll();
    }
}
