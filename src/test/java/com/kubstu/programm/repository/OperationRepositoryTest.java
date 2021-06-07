package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod() {

    }

    @Test
    public void addOne(){
        Operation operation = new Operation();
        operation.setType("once");
        Operation save = operationRepository.save(operation);
        assertThat(save.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Operation operation = new Operation();
        operation.setType("once");
    }

}
