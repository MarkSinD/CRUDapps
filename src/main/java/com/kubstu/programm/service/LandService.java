package com.kubstu.programm.service;

import com.kubstu.programm.entity.Land;
import com.kubstu.programm.exception.LandNotFoundException;
import com.kubstu.programm.repository.LandRepository;
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
public class LandService {
    public static final int LAND_PER_PAGE = 100;

    @Autowired
    private LandRepository landRepository;

    public Page<Land> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, LAND_PER_PAGE, sort);

        if(keyword != null){
            return landRepository.findAll(keyword, pageable);
        }
        return landRepository.findAll(pageable);
    }

    public Land save(Land land) {
        return landRepository.save(land);
    }


    public boolean isNameUnique(Integer id, String name){
        Land landByName = landRepository.getLandByAddress(name);

        if( id != null && landByName == null) {
            return true;
        }
        else if( id != null && landByName != null) {
            if(!landByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && landByName != null) {
            return false;
        }
        else if(id == null && landByName == null) {
            return true;
        }
        return false;
    }

    public Land getLandById(Integer id) throws LandNotFoundException {
        try {
            return landRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new LandNotFoundException("Could not find any land with ID");
        }
    }

    public List<Land> getLands(){
        return (List<Land>) landRepository.findAll();
    }

    public void delete(Integer id) throws LandNotFoundException{
        Long countById = landRepository.countById(id);
        if(countById == null || countById == 0){
            throw new LandNotFoundException("Could not find any land with ID" + id);
        }
        landRepository.deleteById(id);
    }

}
