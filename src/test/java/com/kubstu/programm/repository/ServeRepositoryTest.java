package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.entity.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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
public class ServeRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Tenant tenant = entityManager.find(Tenant.class, 1);

        Serve serve = new Serve();
        serve.setName("Electricity");
        serve.setPrice(4);
        serve.setTenant(tenant);
        Serve savedServe = serviceRepository.save(serve);
        assertThat(savedServe.getId()).isNotEqualTo(0);
    }

}
