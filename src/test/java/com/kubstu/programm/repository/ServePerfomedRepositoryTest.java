package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.entity.ServicePerformed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;

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
public class ServePerfomedRepositoryTest {

    @Autowired
    private ServicePerformedRepository servicePerformedRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Serve serve = entityManager.find(Serve.class, 1);

        ServicePerformed servicePerformed = new ServicePerformed();
        servicePerformed.setDateService(new Date(123));
        servicePerformed.setServe(serve);
        ServicePerformed savedServicePerformed = servicePerformedRepository.save(servicePerformed);
        assertThat(savedServicePerformed.getId()).isNotEqualTo(0);
    }


}
