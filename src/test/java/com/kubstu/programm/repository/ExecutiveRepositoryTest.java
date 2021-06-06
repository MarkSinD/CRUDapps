package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Executive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ExecutiveRepositoryTest {

    @Autowired
    private ExecutiveRepository executiveRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testEmptyMethod(){

    }

    @Test
    public void testAddOne(){
        Executive executive = new Executive();
        executive.setName("Ivan");
        executive.setSurname("Ivanov");
        executive.setPatronymic("Ivanovich");
        executive.setDate(new Date(100000));
        executive.setPosition("director");
        executive.setTelephone(79876543456L);
        Executive savedExecutive = executiveRepository.save(executive);
        assertThat(savedExecutive.getId()).isNotEqualTo(0);
    }

    @Test
    public void testAddListExecutive(){
        Executive executive1 = new Executive();
        executive1.setName("Petr");
        executive1.setSurname("Petrov");
        executive1.setPatronymic("Petrovich");
        executive1.setDate(new Date(104000));
        executive1.setPosition("director");
        executive1.setTelephone(79006543456L);

        Executive executive2 = new Executive();
        executive2.setName("Roman");
        executive2.setSurname("Romanov");
        executive2.setPatronymic("Romanenko");
        executive2.setDate(new Date(150000));
        executive2.setPosition("deputy director");
        executive2.setTelephone(79186543456L);

        Executive executive3 = new Executive();
        executive3.setName("Alex");
        executive3.setSurname("Alexandrov");
        executive3.setPatronymic("Alexandrovoch");
        executive3.setDate(new Date(100030));
        executive3.setPosition("department head");
        executive3.setTelephone(79086543456L);

        Executive executive4 = new Executive();
        executive4.setName("Ivan");
        executive4.setSurname("Petrov");
        executive4.setPatronymic("Ivanovich");
        executive4.setDate(new Date(100030));
        executive4.setPosition("department head");
        executive4.setTelephone(79996543456L);

        List<Executive> executiveList = new ArrayList<>();
        executiveList.add(executive1);
        executiveList.add(executive2);
        executiveList.add(executive3);
        executiveList.add(executive4);

        Iterable<Executive> executivesSaved = executiveRepository.saveAll(executiveList);
        assertThat(executivesSaved.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }


}
