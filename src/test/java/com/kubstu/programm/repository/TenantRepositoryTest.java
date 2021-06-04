package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
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
public class TenantRepositoryTest {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Flat flat = entityManager.find(Flat.class, 1);
        House house = entityManager.find(House.class, 1);


        Tenant tenant = new Tenant();
        tenant.setFlat(flat);
        tenant.setHouse(house);
        tenant.setName("Ivan");
        tenant.setSurname("Ivanov");
        Tenant savedTenant = tenantRepository.save(tenant);
        assertThat(savedTenant.getId()).isNotEqualTo(0);
    }


}
