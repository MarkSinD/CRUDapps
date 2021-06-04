package com.kubstu.programm.service;

import com.kubstu.programm.entity.ServicePerformed;
import com.kubstu.programm.exception.ServePerformedNotFoundException;
import com.kubstu.programm.repository.ServicePerformedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ServicePerformedService {
    public static final int SERVICE_PERFORMED_PER_PAGE = 4;

    @Autowired
    private ServicePerformedRepository servicePerformedRepository;

    public Page<ServicePerformed> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, SERVICE_PERFORMED_PER_PAGE, sort);

        if(keyword != null){
            return servicePerformedRepository.findAll(keyword, pageable);
        }
        return servicePerformedRepository.findAll(pageable);
    }

    public ServicePerformed save(ServicePerformed servicePerformed) {
        return servicePerformedRepository.save(servicePerformed);
    }

    public boolean isNameUnique(Integer id, String name){
        ServicePerformed servicePerformedByName = servicePerformedRepository.getServicePerformedByName(name);
        if( id != null && servicePerformedByName == null) {
            return true;
        }
        else if( id != null && servicePerformedByName != null) {
            if(!servicePerformedByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && servicePerformedByName != null) {
            return false;
        }
        else if(id == null && servicePerformedByName == null) {
            return true;
        }
        return false;
    }

    public ServicePerformed getServicePerformedById(Integer id) throws ServePerformedNotFoundException {
        try {
            return servicePerformedRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ServePerformedNotFoundException("Could not find any organization with ID");
        }
    }

    public void delete(Integer id) throws ServePerformedNotFoundException {
        Long countById = servicePerformedRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ServePerformedNotFoundException("Could not find any organization with ID" + id);
        }
        servicePerformedRepository.deleteById(id);
    }
}
