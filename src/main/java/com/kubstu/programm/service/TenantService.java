package com.kubstu.programm.service;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.exception.TenantNotFoundException;
import com.kubstu.programm.repository.OrganizationRepository;
import com.kubstu.programm.repository.TenantRepository;
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
public class TenantService {
    public static final int TENANT_PER_PAGE = 4;

    @Autowired
    private TenantRepository tenantRepository;

    public Page<Tenant> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, TENANT_PER_PAGE, sort);

        if(keyword != null){
            return tenantRepository.findAll(keyword, pageable);
        }
        return tenantRepository.findAll(pageable);
    }

    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public boolean isNameUnique(Integer id, String name){
        Tenant tenantByName = tenantRepository.getTenantByName(name);
        if( id != null && tenantByName == null) {
            return true;
        }
        else if( id != null && tenantByName != null) {
            if(!tenantByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && tenantByName != null) {
            return false;
        }
        else if(id == null && tenantByName == null) {
            return true;
        }
        return false;
    }

    public Tenant getTenantById(Integer id) throws TenantNotFoundException {
        try {
            return tenantRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new TenantNotFoundException("Could not find any tenant with ID");
        }
    }

    public void delete(Integer id) throws TenantNotFoundException {
        Long countById = tenantRepository.countById(id);
        if(countById == null || countById == 0){
            throw new TenantNotFoundException("Could not find any organization with ID" + id);
        }
        tenantRepository.deleteById(id);
    }

    public List<Tenant> getTenants(){
        return (List<Tenant>) tenantRepository.findAll();
    }
}
