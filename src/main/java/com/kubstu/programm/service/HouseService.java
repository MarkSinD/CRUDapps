package com.kubstu.programm.service;

import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.HouseNotFoundException;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.repository.HouseRepository;
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
public class HouseService {
    public static final int HOUSE_PER_PAGE = 4;

    @Autowired
    private HouseRepository houseRepository;


    public Page<House> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, HOUSE_PER_PAGE, sort);

        if(keyword != null){
            return houseRepository.findAll(keyword, pageable);
        }
        return houseRepository.findAll(pageable);
    }

    public House save(House house) {
        return houseRepository.save(house);
    }

    public boolean isNameUnique(Integer id, String name){
        House houseByName = houseRepository.getHouseByName(name);
        if( id != null && houseByName == null) {
            return true;
        }
        else if( id != null && houseByName != null) {
            if(!houseByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && houseByName != null) {
            return false;
        }
        else if(id == null && houseByName == null) {
            return true;
        }
        return false;
    }

    public House getHouseById(Integer id) throws HouseNotFoundException {
        try {
            return houseRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new HouseNotFoundException("Could not find any organization with ID");
        }
    }

    public List<House> getHouses(){
        return (List<House>) houseRepository.findAll();
    }

    public void delete(Integer id) throws HouseNotFoundException {
        Long countById = houseRepository.countById(id);
        if(countById == null || countById == 0){
            throw new HouseNotFoundException("Could not find any organization with ID" + id);
        }
        houseRepository.deleteById(id);
    }

}
