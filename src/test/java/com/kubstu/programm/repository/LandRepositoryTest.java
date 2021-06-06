package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Land;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class LandRepositoryTest {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testEmptyMethod(){

    }

    @Test
    public void testAddOne(){
        Land land = new Land();
        land.setAddress("Kransnodar");
        land.setSize(4);
        land.setAffiliation("city property");
        land.setNotes("Non");
        Land savedLand = landRepository.save(land);

        assertThat(savedLand.getId()).isNotEqualTo(0);
    }

    @Test
    public void testAddList(){
        Land land1 = new Land();
        land1.setAddress("Moscow");
        land1.setSize(1);
        land1.setAffiliation("city property");
        land1.setNotes("Non");

        Land land2 = new Land();
        land2.setAddress("Kiev");
        land2.setSize(2);
        land2.setAffiliation("city property");
        land2.setNotes("Non");

        Land land3 = new Land();
        land3.setAddress("Omsk");
        land3.setSize(3);
        land3.setAffiliation("city property");
        land3.setNotes("Non");

        Land land4 = new Land();
        land4.setAddress("Moscow");
        land4.setSize(4);
        land4.setAffiliation("city property");
        land4.setNotes("Non");

        List<Land> savedLands = new ArrayList<>();
        savedLands.add(land1);
        savedLands.add(land2);
        savedLands.add(land3);
        savedLands.add(land4);

        Iterable<Land> lands = landRepository.saveAll(savedLands);
        assertThat(lands.spliterator().getExactSizeIfKnown()).isEqualTo(4);

    }
}
