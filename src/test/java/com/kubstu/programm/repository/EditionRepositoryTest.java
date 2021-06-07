package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Edition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EditionRepositoryTest {

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod() {

    }

    @Test
    public void addOne(){
        Edition edition = new Edition();
        edition.setName("Eksmo");
        edition.setAttributeName("clean code. build, analyze and refactor");
        edition.setPrice(25);
        Edition save = editionRepository.save(edition);
        assertThat(save.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Edition edition1 = new Edition();
        edition1.setName("Eksmo");
        edition1.setAttributeName("refactoring. Improving existing code");
        edition1.setPrice(25);

        Edition edition2 = new Edition();
        edition2.setName("AST");
        edition2.setAttributeName("pragmatic programmer. the path from apprentice to master");
        edition2.setPrice(23);

        Edition edition3 = new Edition();
        edition3.setName("ABC");
        edition3.setAttributeName("perfect code. Master Class");
        edition3.setPrice(15);

        Edition edition4 = new Edition();
        edition4.setName("Alpina Publisher");
        edition4.setAttributeName("head first. Design patterns");
        edition4.setPrice(9);

        Edition edition5 = new Edition();
        edition5.setName("Rosman");
        edition5.setAttributeName("mythical man-month");
        edition5.setPrice(40);

        List<Edition> editionList = new ArrayList<>();
        editionList.add(edition1);
        editionList.add(edition2);
        editionList.add(edition3);
        editionList.add(edition4);
        editionList.add(edition5);

        Iterable<Edition> editions = editionRepository.saveAll(editionList);
        assertThat(editions.spliterator().getExactSizeIfKnown()).isEqualTo(5);


    }


}
