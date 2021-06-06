package com.kubstu.programm.service;

import com.kubstu.programm.entity.Information;
import com.kubstu.programm.exception.InformationNotFoundException;
import com.kubstu.programm.repository.InformationRepository;
import nonapi.io.github.classgraph.json.JSONUtils;
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
public class InformationService {
    public static final int INFORMATION_PER_PAGE = 100;

    @Autowired
    private InformationRepository informationRepository;

    public Page<Information> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, INFORMATION_PER_PAGE, sort);

        if(keyword != null){
            return informationRepository.findAll(keyword, pageable);
        }
        return informationRepository.findAll(pageable);
    }

    public Information save(Information information) {
        return informationRepository.save(information);
    }


    public boolean isNameUnique(Integer id, String name){
        Information informationByName = informationRepository.getInformationByName(name);

        if( id != null && informationByName == null) {
            return true;
        }
        else if( id != null && informationByName != null) {
            if(!informationByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && informationByName != null) {
            return false;
        }
        else if(id == null && informationByName == null) {
            return true;
        }
        return false;
    }

    public Information getInformationById(Integer id) throws InformationNotFoundException {
        try {
            return informationRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new InformationNotFoundException("Could not find any information with ID");
        }
    }

    public List<Information> getInformations(){
        return (List<Information>) informationRepository.findAll();
    }

    public void delete(Integer id) throws InformationNotFoundException{
        Long countById = informationRepository.countById(id);
        if(countById == null || countById == 0){
            throw new InformationNotFoundException("Could not find any information with ID" + id);
        }
        informationRepository.deleteById(id);
    }

}
