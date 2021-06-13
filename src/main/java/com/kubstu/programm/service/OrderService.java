package com.kubstu.programm.service;

import com.kubstu.programm.entity.Order;
import com.kubstu.programm.exception.OrderNotFoundException;
import com.kubstu.programm.repository.OrderRepository;
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
public class OrderService {
    public static final int ORDER_PER_PAGE = 100;

    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, ORDER_PER_PAGE, sort);

        if(keyword != null){
            return orderRepository.findAll(keyword, pageable);
        }
        return orderRepository.findAll(pageable);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }


    public boolean isNameUnique(Integer id, String name){
        Order orderByEmail = orderRepository.getOrderByEmail(id);

        if( id != null && orderByEmail == null) {
            return true;
        }
        else if( id != null && orderByEmail != null) {
            if(!orderByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && orderByEmail != null) {
            return false;
        }
        else if(id == null && orderByEmail == null) {
            return true;
        }
        return false;
    }

    public Order getOrderById(Integer id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new OrderNotFoundException("Could not find any order with ID");
        }
    }

    public List<Order> getOrders(){
        return (List<Order>) orderRepository.findAll();
    }

    public void delete(Integer id) throws OrderNotFoundException{
        Long countById = orderRepository.countById(id);
        if(countById == null || countById == 0){
            throw new OrderNotFoundException("Could not find any order with ID" + id);
        }
        orderRepository.deleteById(id);
    }
}

