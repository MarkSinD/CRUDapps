package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void addOne(){
        Order order = new Order();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        order.setDate(date);
        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){

    }


}
