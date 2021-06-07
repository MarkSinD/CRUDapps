package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Subscriber;
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
public class SubscriberRepositoryTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod() {

    }


    @Test
    public void addOne(){
        Subscriber subscriber = new Subscriber();
        subscriber.setName("Ivan");
        subscriber.setSurname("Ivanov");
        subscriber.setPatronymic("Ivanovich");
        subscriber.setAddress("Gagarina, 103, Krasnodar");

        Subscriber saved = subscriberRepository.save(subscriber);
        assertThat(saved.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Subscriber subscriber1 = new Subscriber();
        subscriber1.setName("Mikhail");
        subscriber1.setSurname("Myasnikov");
        subscriber1.setPatronymic("Anatolyevich");
        subscriber1.setAddress("Dombayskaya, 10/1, Krasnodar");

        Subscriber subscriber2 = new Subscriber();
        subscriber2.setName("Alexey");
        subscriber2.setSurname("Ukhvatov");
        subscriber2.setPatronymic("Yurievich");
        subscriber2.setAddress("Stavropolskaya, 205, Krasnodar");

        Subscriber subscriber3 = new Subscriber();
        subscriber3.setName("Sergey");
        subscriber3.setSurname("Volkov");
        subscriber3.setPatronymic("Alexandrovich");
        subscriber3.setAddress("Sadovaya, 110, Krasnodar");

        Subscriber subscriber4 = new Subscriber();
        subscriber4.setName("Ivan");
        subscriber4.setSurname("Ivanov");
        subscriber4.setPatronymic("Ivanovich");
        subscriber4.setAddress("Vorovskogo, 15, Krasnodar");

        Subscriber subscriber5 = new Subscriber();
        subscriber5.setName("Oleg");
        subscriber5.setSurname("Kononenko");
        subscriber5.setPatronymic("Dmitrievich");
        subscriber5.setAddress("Spacious, 7a, Krasnodar");

        List<Subscriber> subscriberList = new ArrayList<>();
        subscriberList.add(subscriber1);
        subscriberList.add(subscriber2);
        subscriberList.add(subscriber3);
        subscriberList.add(subscriber4);
        subscriberList.add(subscriber5);

        Iterable<Subscriber> subscribers = subscriberRepository.saveAll(subscriberList);
        assertThat(subscribers.spliterator().getExactSizeIfKnown()).isEqualTo(5);


    }
}
