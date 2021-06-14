package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Serve;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ServeRepositoryTest {

    @Autowired
    private ServeRepository serveRepository;

    @Test
    public void addOne(){
        Serve serve = new Serve();
        Serve savedServe = serveRepository.save(serve);
        assertThat(savedServe.getId()).isNotEqualTo(0);
    }

}
