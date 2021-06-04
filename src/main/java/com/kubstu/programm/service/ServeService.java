package com.kubstu.programm.service;

import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.exception.ServeNotFoundException;
import com.kubstu.programm.repository.ServiceRepository;
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
public class ServeService {
    public static final int SERVE_PER_PAGE = 4;

    @Autowired
    private ServiceRepository serveRepository;

    public Page<Serve> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, SERVE_PER_PAGE, sort);

        if(keyword != null){
            return serveRepository.findAll(keyword, pageable);
        }
        return serveRepository.findAll(pageable);
    }

    public Serve save(Serve serve) {
        return serveRepository.save(serve);
    }

    public boolean isNameUnique(Integer id, String name){
        Serve serveByName = serveRepository.getServeByName(name);
        if( id != null && serveByName == null) {
            return true;
        }
        else if( id != null && serveByName != null) {
            if(!serveByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && serveByName != null) {
            return false;
        }
        else if(id == null && serveByName == null) {
            return true;
        }
        return false;
    }

    public Serve getServeById(Integer id) throws ServeNotFoundException {
        try {
            return serveRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ServeNotFoundException("Could not find any service with ID");
        }
    }

    public void delete(Integer id) throws ServeNotFoundException {
        Long countById = serveRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ServeNotFoundException("Could not find any service with ID" + id);
        }
        serveRepository.deleteById(id);
    }

    public List<Serve> getServes(){
        return (List<Serve>) serveRepository.findAll();
    }
}
