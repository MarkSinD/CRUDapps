package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Flat;
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

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FlatRepositoryTest {

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyTest(){}

    @Test
    public void addOne(){
        House house = entityManager.find(House.class, 1);

        Flat flat = new Flat();
        flat.setCountRooms(4);
        flat.setHouse(house);
        flat.setLivingSpace(50);
        Flat savedFlat = flatRepository.save(flat);
        assertThat(savedFlat.getId()).isNotEqualTo(0);
    }

}
