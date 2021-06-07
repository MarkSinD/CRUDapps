package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Subscription;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.SubscriptionNotFoundException;
import com.kubstu.programm.repository.SubscriptionRepository;
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
public class SubsrciptionService {
    public static final int SUBSRCIPTION_PER_PAGE = 100;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Page<Subscription> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, SUBSRCIPTION_PER_PAGE, sort);

        if(keyword != null){
            return subscriptionRepository.findAll(keyword, pageable);
        }
        return subscriptionRepository.findAll(pageable);
    }

    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }


    public boolean isNameUnique(Integer id, String name){
        Subscription subscriptionByEmail = subscriptionRepository.getSubscriptionByEmail(name);

        if( id != null && subscriptionByEmail == null) {
            return true;
        }
        else if( id != null && subscriptionByEmail != null) {
            if(!subscriptionByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && subscriptionByEmail != null) {
            return false;
        }
        else if(id == null && subscriptionByEmail == null) {
            return true;
        }
        return false;
    }

    public Subscription getSubscriptionById(Integer id) throws SubscriptionNotFoundException {
        try {
            return subscriptionRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new SubscriptionNotFoundException("Could not find any subscription with ID");
        }
    }

    public List<Subscription> getSubscriptions(){
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    public void delete(Integer id) throws SubscriptionNotFoundException {
        Long countById = subscriptionRepository.countById(id);
        if(countById == null || countById == 0){
            throw new SubscriptionNotFoundException("Could not find any subscription with ID" + id);
        }
        subscriptionRepository.deleteById(id);
    }
}