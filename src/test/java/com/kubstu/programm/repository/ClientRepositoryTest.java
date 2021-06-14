package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void addOne(){
        Client client = new Client();
        client.setName("Ivan");
        client.setSurname("Ivanov");
        client.setPatronymic("Ivanovich");
        Date date = Date.valueOf("1992-08-16");
        client.setDateBitrh(date);
        Client savedClient = clientRepository.save(client);
        assertThat(savedClient.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Client client = new Client();
        Client savedClient = clientRepository.save(client);
        assertThat(savedClient.getId()).isNotEqualTo(0);
    }
}
