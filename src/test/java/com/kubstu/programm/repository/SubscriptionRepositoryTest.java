package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Subscription;
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
public class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod() {

    }

    @Test
    public void addOne(){
        Subscription subscription = new Subscription();
        subscription.setSubscriptionDate(new Date(10000));
        subscription.setSubscriptionTerm(10);
        Subscription save = subscriptionRepository.save(subscription);
        assertThat(save.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Subscription subscription1 = new Subscription();
        subscription1.setSubscriptionDate(new Date(10000));
        subscription1.setSubscriptionTerm(14);

        Subscription subscription2 = new Subscription();
        subscription2.setSubscriptionDate(new Date(10000));
        subscription2.setSubscriptionTerm(6);

        Subscription subscription3 = new Subscription();
        subscription3.setSubscriptionDate(new Date(10000));
        subscription3.setSubscriptionTerm(3);

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription1);
        subscriptionList.add(subscription2);
        subscriptionList.add(subscription3);
        Iterable<Subscription> subscriptions = subscriptionRepository.saveAll(subscriptionList);
        assertThat(subscriptions.spliterator().getExactSizeIfKnown()).isEqualTo(3);
    }
}
