package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Receiving;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.ReceivingNotFoundException;
import com.kubstu.programm.repository.ReceivingRepository;
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
public class ReceivingService {
    public static final int RECEIVING_PER_PAGE = 100;

    @Autowired
    private ReceivingRepository receivingRepository;

    public Page<Receiving> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, RECEIVING_PER_PAGE, sort);

        if(keyword != null){
            return receivingRepository.findAll(keyword, pageable);
        }
        return receivingRepository.findAll(pageable);
    }

    public Receiving save(Receiving receiving) {
        return receivingRepository.save(receiving);
    }


    public boolean isNameUnique(Integer id, String name){
        Receiving repositoryDispatchByEmail = receivingRepository.getReceivingByEmail(id);

        if( id != null && repositoryDispatchByEmail == null) {
            return true;
        }
        else if( id != null && repositoryDispatchByEmail != null) {
            if(!repositoryDispatchByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && repositoryDispatchByEmail != null) {
            return false;
        }
        else if(id == null && repositoryDispatchByEmail == null) {
            return true;
        }
        return false;
    }

    public Receiving getReceivingById(Integer id) throws ReceivingNotFoundException {
        try {
            return receivingRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ReceivingNotFoundException("Could not find any receiving with ID");
        }
    }

    public List<Receiving> getReceivings(){
        return (List<Receiving>) receivingRepository.findAll();
    }

    public void delete(Integer id) throws ReceivingNotFoundException {
        Long countById = receivingRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ReceivingNotFoundException("Could not find any receiving with ID" + id);
        }
        receivingRepository.deleteById(id);
    }

}
