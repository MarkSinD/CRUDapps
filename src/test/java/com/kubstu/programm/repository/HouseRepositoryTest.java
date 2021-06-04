package com.kubstu.programm.repository;

import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
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
public class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Organization organization = entityManager.find(Organization.class, 1);

        House house = new House();
        house.setOrganization(organization);
        house.setAddress("Anam");
        house.setCountApartments(200);
        House savedHouse = houseRepository.save(house);
        assertThat(savedHouse.getId()).isNotEqualTo(0);
    }
}
