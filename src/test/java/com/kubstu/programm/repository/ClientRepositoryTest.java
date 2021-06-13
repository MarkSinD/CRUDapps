package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

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
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void addOne(){
        Client client = new Client();
        client.setName("Ivan");
        client.setSurname("Ivanov");
        client.setPatronymic("Ivanovich");
        client.setAddress("Gagarina, 103, Krasnodar");
        client.setTelephone(78612508418L);
        clientRepository.save(client);
    }

    @Test
    public void addList(){
        List<Client> clients = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("Sergey");
        client1.setSurname("Arefiev");
        client1.setPatronymic("Anatolievich");
        client1.setAddress("Vorovskogo, 15, Krasnodar");
        client1.setTelephone(78612508418L);

        Client client2 = new Client();
        client2.setName("Sergey");
        client2.setSurname("Avdeev");
        client2.setPatronymic("Vasilievich");
        client2.setAddress("Dombayskaya, 10/1");
        client2.setTelephone(75234232412L);


        Client client3 = new Client();
        client3.setName("Sergey");
        client3.setSurname("Bavilin");
        client3.setPatronymic("Mikhailovich");
        client3.setAddress("Stavropolskaya, 205, Krasnodar");
        client3.setTelephone(71231508414L);


        Client client4 = new Client();
        client4.setName("Nikolay");
        client4.setSurname("Belyaev");
        client4.setPatronymic("Alexandrovich");
        client4.setAddress("Sadovaya, 110, Krasnodar");
        client4.setTelephone(75235508413L);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        Iterable<Client> savedClients = clientRepository.saveAll(clients);
        assertThat(savedClients.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }


}
