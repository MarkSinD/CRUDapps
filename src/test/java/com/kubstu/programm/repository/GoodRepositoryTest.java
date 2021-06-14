package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Good;
import com.kubstu.programm.entity.Provider;
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
public class GoodRepositoryTest {
    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Provider provider = entityManager.find(Provider.class, 1);

        Good good = new Good();
        good.setTitle("photo album");
        good.setPrice(25);
        good.setQuantity(5);
        good.setMainImage("defualt.png");
        good.setProvider(provider);

        Good savedGood = goodRepository.save(good);
        assertThat(savedGood.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Provider provider2 = entityManager.find(Provider.class, 2);
        Provider provider3 = entityManager.find(Provider.class, 3);
        Provider provider4 = entityManager.find(Provider.class, 4);
        Provider provider5 = entityManager.find(Provider.class, 5);

        Good good2 = new Good();
        good2.setTitle("camera roll");
        good2.setPrice(15);
        good2.setQuantity(4);
        good2.setMainImage("defualt.png");
        good2.setProvider(provider2);

        Good good3 = new Good();
        good3.setTitle("flash drive");
        good3.setPrice(10);
        good3.setQuantity(3);
        good3.setMainImage("defualt.png");
        good3.setProvider(provider3);

        Good good4 = new Good();
        good4.setTitle("DVD disk");
        good4.setPrice(5);
        good4.setQuantity(6);
        good4.setMainImage("defualt.png");
        good4.setProvider(provider4);

        Good good5 = new Good();
        good5.setTitle("CD disk");
        good5.setPrice(4);
        good5.setQuantity(4);
        good5.setMainImage("defualt.png");
        good5.setProvider(provider5);

        List<Good> goodList = new ArrayList<>();
        goodList.add(good2);
        goodList.add(good3);
        goodList.add(good4);
        goodList.add(good5);

        Iterable<Good> goods = goodRepository.saveAll(goodList);
        assertThat(goods.spliterator().getExactSizeIfKnown()).isEqualTo(4);
    }














}
