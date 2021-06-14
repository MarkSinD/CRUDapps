package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Book;
import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Good;
import com.kubstu.programm.entity.Serve;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addFirst(){
        Book book = new Book();

        Good good1 = entityManager.find(Good.class, 1);
        Good good2 = entityManager.find(Good.class, 2);
        Good good3 = entityManager.find(Good.class, 3);
        Set<Good> goodSet = new HashSet<>();
        goodSet.add(good1);
        goodSet.add(good2);
        goodSet.add(good3);


        Serve serve1 = entityManager.find(Serve.class, 1);
        Serve serve2 = entityManager.find(Serve.class, 2);
        Set<Serve> serveSet = new HashSet<>();
        serveSet.add(serve1);
        serveSet.add(serve2);


        Client client = entityManager.find(Client.class, 1);


        book.setClient(client);
        Date date = Date.valueOf("2020-08-01");
        book.setDate(date);
        book.setGoods(goodSet);
        book.setServes(serveSet);

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotEqualTo(0);
    }

    @Test
    public void addSecond(){
        Book book = new Book();

        Good good1 = entityManager.find(Good.class, 3);
        Good good2 = entityManager.find(Good.class, 4);
        Good good3 = entityManager.find(Good.class, 5);
        Set<Good> goodSet = new HashSet<>();
        goodSet.add(good1);
        goodSet.add(good2);
        goodSet.add(good3);


        Serve serve1 = entityManager.find(Serve.class, 2);
        Serve serve2 = entityManager.find(Serve.class, 3);
        Set<Serve> serveSet = new HashSet<>();
        serveSet.add(serve1);
        serveSet.add(serve2);


        Client client = entityManager.find(Client.class, 2);


        book.setClient(client);
        Date date = Date.valueOf("2020-09-10");
        book.setDate(date);
        book.setGoods(goodSet);
        book.setServes(serveSet);

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotEqualTo(0);
    }

    @Test
    public void addThird(){
        Book book = new Book();

        Good good1 = entityManager.find(Good.class, 1);
        Set<Good> goodSet = new HashSet<>();
        goodSet.add(good1);

        Serve serve1 = entityManager.find(Serve.class, 1);
        Set<Serve> serveSet = new HashSet<>();
        serveSet.add(serve1);

        Client client = entityManager.find(Client.class, 5);

        book.setClient(client);
        Date date = Date.valueOf("2020-10-11");
        book.setDate(date);
        book.setGoods(goodSet);
        book.setServes(serveSet);

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotEqualTo(0);
    }

    @Test
    public void addForth(){
        Book book = new Book();

        Good good1 = entityManager.find(Good.class, 1);
        Set<Good> goodSet = new HashSet<>();
        goodSet.add(good1);


        Serve serve2 = entityManager.find(Serve.class, 3);
        Set<Serve> serveSet = new HashSet<>();
        serveSet.add(serve2);


        Client client = entityManager.find(Client.class, 4);


        book.setClient(client);
        Date date = Date.valueOf("2021-01-01");
        book.setDate(date);
        book.setGoods(goodSet);
        book.setServes(serveSet);

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotEqualTo(0);
    }
}












































































