package com.kubstu.programm.service;

import com.kubstu.programm.entity.Provider;
import com.kubstu.programm.exception.ProviderNotFoundException;
import com.kubstu.programm.repository.ProviderRepository;
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
public class ProviderService {
    public static final int PROVIDER_PER_PAGE = 100;

    @Autowired
    private ProviderRepository providerRepository;

    public Page<Provider> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, PROVIDER_PER_PAGE, sort);

        if(keyword != null){
            return providerRepository.findAll(keyword, pageable);
        }
        return providerRepository.findAll(pageable);
    }

    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }


    public boolean isNameUnique(Integer id, String name){
        Provider providerByName = providerRepository.getProviderByName(name);

        if( id != null && providerByName == null) {
            return true;
        }
        else if( id != null && providerByName != null) {
            if(!providerByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && providerByName != null) {
            return false;
        }
        else if(id == null && providerByName == null) {
            return true;
        }
        return false;
    }

    public Provider getProviderById(Integer id) throws ProviderNotFoundException {
        try {
            return providerRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ProviderNotFoundException("Could not find any provider with ID");
        }
    }

    public List<Provider> getProviders(){
        return (List<Provider>) providerRepository.findAll();
    }

    public void delete(Integer id) throws ProviderNotFoundException{
        Long countById = providerRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ProviderNotFoundException("Could not find any provider with ID" + id);
        }
        providerRepository.deleteById(id);
    }
}