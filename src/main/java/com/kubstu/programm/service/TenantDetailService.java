package com.kubstu.programm.service;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.TenantDetail;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.exception.TenantDetailNotFoundException;
import com.kubstu.programm.repository.OrganizationRepository;
import com.kubstu.programm.repository.TenantDetailRepository;
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
public class TenantDetailService {
    public static final int TENANT_DETAIL_PER_PAGE = 4;

    @Autowired
    private TenantDetailRepository tenantDetailRepository;


    public Page<TenantDetail> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, TENANT_DETAIL_PER_PAGE, sort);

        if(keyword != null){
            return tenantDetailRepository.findAll(keyword, pageable);
        }
        return tenantDetailRepository.findAll(pageable);
    }

    public TenantDetail save(TenantDetail tenantDetail) {
        return tenantDetailRepository.save(tenantDetail);
    }

    public boolean isNameUnique(Integer id, String name){
        TenantDetail tenantDetailByName = tenantDetailRepository.getTenantDetailByName(name);
        if( id != null && tenantDetailByName == null) {
            return true;
        }
        else if( id != null && tenantDetailByName != null) {
            if(!tenantDetailByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && tenantDetailByName != null) {
            return false;
        }
        else if(id == null && tenantDetailByName == null) {
            return true;
        }
        return false;
    }

    public TenantDetail getTenantDetailById(Integer id) throws TenantDetailNotFoundException {
        try {
            return tenantDetailRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new TenantDetailNotFoundException("Could not find any tenant detail with ID");
        }
    }

    public void delete(Integer id) throws TenantDetailNotFoundException {
        Long countById = tenantDetailRepository.countById(id);
        if(countById == null || countById == 0){
            throw new TenantDetailNotFoundException("Could not find any tenant detail with ID" + id);
        }
        tenantDetailRepository.deleteById(id);
    }
}
