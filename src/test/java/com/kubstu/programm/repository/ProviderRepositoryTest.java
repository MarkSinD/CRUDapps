package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProviderRepositoryTest {
    @Autowired
    private ProviderRepository providerRepository;

    @Test
    public void addOne(){
        Provider provider = new Provider();
        Provider savedProvider = providerRepository.save(provider);
        assertThat(savedProvider.getId()).isNotEqualTo(0);

    }
}
