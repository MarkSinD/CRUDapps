package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProviderRepositoryTest {

    @Autowired
    private ProviderRepository providerRepository;


    @Test
    public void addOne(){
        Provider provider1 = new Provider();
        provider1.setName("Anatoly");
        provider1.setSurname("Kopyrkin");
        provider1.setTelephone(79895643676L);
        provider1.setAddress("Sormovskaya, 104/2");
        Provider savedProvider = providerRepository.save(provider1);
        assertThat(savedProvider.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Provider provider = new Provider();
        provider.setName("Roman");
        provider.setSurname("Korovushkin");
        provider.setTelephone(79125643656L);
        provider.setAddress("Heroes-Scouts, 52/1");

        Provider provider2 = new Provider();
        provider2.setName("Sergey");
        provider2.setSurname("Korshunov");
        provider2.setTelephone(79885643567L);
        provider2.setAddress("Promyshlennaya, 88");

        Provider provider3 = new Provider();
        provider3.setName("Vyacheslav");
        provider3.setSurname("Krotevich");
        provider3.setTelephone(79865643653L);
        provider3.setAddress("Chernyshevsky, 36");

        Provider provider4 = new Provider();
        provider4.setName("Konstantin");
        provider4.setSurname("Krasnikov");
        provider4.setTelephone(79075643687L);
        provider4.setAddress("North, 449");

        List<Provider> providerList = new ArrayList<>();
        providerList.add(provider);
        providerList.add(provider2);
        providerList.add(provider3);
        providerList.add(provider4);

        providerRepository.saveAll(providerList);
    }


}
