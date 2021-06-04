package com.kubstu.programm.service;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.OrganizationNotFoundException;
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
public class OrganizationService {
    public static final int ORGANIZATION_PER_PAGE = 4;

    @Autowired
    private OrganizationRepository organizationRepository;

    public Page<Organization> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, ORGANIZATION_PER_PAGE, sort);

        if(keyword != null){
            return organizationRepository.findAll(keyword, pageable);
        }
        return organizationRepository.findAll(pageable);
    }

    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }


    public boolean isNameUnique(Integer id, String name){
        Organization organizationByName = organizationRepository.getOrganizationByName(name);

        if( id != null && organizationByName == null) {
            return true;
        }
        else if( id != null && organizationByName != null) {
            if(!organizationByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && organizationByName != null) {
            return false;
        }
        else if(id == null && organizationByName == null) {
            return true;
        }
        return false;
    }

    public Organization getOrganizationById(Integer id) throws OrganizationNotFoundException {
        try {
            return organizationRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new OrganizationNotFoundException("Could not find any organization with ID");
        }
    }

    public List<Organization> getOrganizations(){
        return (List<Organization>) organizationRepository.findAll();
    }

    public void delete(Integer id) throws OrganizationNotFoundException{
        Long countById = organizationRepository.countById(id);
        if(countById == null || countById == 0){
            throw new OrganizationNotFoundException("Could not find any user with ID" + id);
        }
        organizationRepository.deleteById(id);
    }

}
