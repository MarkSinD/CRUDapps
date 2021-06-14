package com.kubstu.programm.service;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Good;
import com.kubstu.programm.exception.GoodNotFoundException;
import com.kubstu.programm.repository.GoodRepository;
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
public class GoodService {
    public static final int GOOD_PER_PAGE = 100;

    @Autowired
    private GoodRepository goodRepository;

    public Page<Good> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, GOOD_PER_PAGE, sort);

        if(keyword != null){
            return goodRepository.findAll(keyword, pageable);
        }
        return goodRepository.findAll(pageable);
    }

    public Good save(Good good) {
        return goodRepository.save(good);
    }


    public boolean isNameUnique(Integer id, String name){
        Good goodByName = goodRepository.getGoodByName(name);

        if( id != null && goodByName == null) {
            return true;
        }
        else if( id != null && goodByName != null) {
            if(!goodByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && goodByName != null) {
            return false;
        }
        else if(id == null && goodByName == null) {
            return true;
        }
        return false;
    }

    public Good getGoodById(Integer id) throws GoodNotFoundException {
        try {
            return goodRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new GoodNotFoundException("Could not find any good with ID");
        }
    }

    public List<Good> getGoods(){
        return (List<Good>) goodRepository.findAll();
    }

    public void delete(Integer id) throws GoodNotFoundException{
        Long countById = goodRepository.countById(id);
        if(countById == null || countById == 0){
            throw new GoodNotFoundException("Could not find any good with ID" + id);
        }
        goodRepository.deleteById(id);
    }

}