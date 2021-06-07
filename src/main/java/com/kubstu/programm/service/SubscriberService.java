package com.kubstu.programm.service;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Subscriber;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.SubscriberNotFoundException;
import com.kubstu.programm.repository.SubscriberRepository;
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
public class SubscriberService {
    public static final int SUBSCRIBER_PER_PAGE = 100;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Page<Subscriber> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, SUBSCRIBER_PER_PAGE, sort);

        if(keyword != null){
            return subscriberRepository.findAll(keyword, pageable);
        }
        return subscriberRepository.findAll(pageable);
    }

    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }


    public boolean isNameUnique(Integer id, String name){
        Subscriber subscriber = subscriberRepository.getSubscriberByEmail(name);

        if( id != null && subscriber == null) {
            return true;
        }
        else if( id != null && subscriber != null) {
            if(!subscriber.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && subscriber != null) {
            return false;
        }
        else if(id == null && subscriber == null) {
            return true;
        }
        return false;
    }

    public Subscriber getSubscriberById(Integer id) throws SubscriberNotFoundException {
        try {
            return subscriberRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new SubscriberNotFoundException("Could not find any subscriber with ID");
        }
    }

    public List<Subscriber> getSubscribers(){
        return (List<Subscriber>) subscriberRepository.findAll();
    }

    public void delete(Integer id) throws SubscriberNotFoundException {
        Long countById = subscriberRepository.countById(id);
        if(countById == null || countById == 0){
            throw new SubscriberNotFoundException("Could not find any administration with ID" + id);
        }
        subscriberRepository.deleteById(id);
    }

}