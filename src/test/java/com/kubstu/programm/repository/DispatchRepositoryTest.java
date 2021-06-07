package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Dispatch;
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
public class DispatchRepositoryTest {

    @Autowired
    private DispatchRepository dispatchRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod(){

    }

    @Test
    public void addOne(){
        Dispatch dispatch = new Dispatch();
        dispatch.setSendingType("Post of Russia");
        dispatch.setSenderDate(new Date(10000));
        dispatch.setRecipientData(new Date(11000));
        dispatch.setWeight(15);
        dispatch.setValue((10));

        Dispatch savedDispatch = dispatchRepository.save(dispatch);
        assertThat(savedDispatch.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Dispatch dispatch1 = new Dispatch();
        dispatch1.setSendingType("DPD");
        dispatch1.setSenderDate(new Date(10000));
        dispatch1.setRecipientData(new Date(11000));
        dispatch1.setWeight(12);
        dispatch1.setValue((11));

        Dispatch dispatch2 = new Dispatch();
        dispatch2.setSendingType("CDEK");
        dispatch2.setSenderDate(new Date(10000));
        dispatch2.setRecipientData(new Date(11000));
        dispatch2.setWeight(1);
        dispatch2.setValue((12));

        Dispatch dispatch3 = new Dispatch();
        dispatch3.setSendingType("Boxberry");
        dispatch3.setSenderDate(new Date(10000));
        dispatch3.setRecipientData(new Date(11000));
        dispatch3.setWeight(3);
        dispatch3.setValue((5));

        Dispatch dispatch4 = new Dispatch();
        dispatch4.setSendingType("Imbirbar");
        dispatch4.setSenderDate(new Date(10000));
        dispatch4.setRecipientData(new Date(11000));
        dispatch4.setWeight(7);
        dispatch4.setValue((5));

        Dispatch dispatch5 = new Dispatch();
        dispatch5.setSendingType("FARFOR");
        dispatch5.setSenderDate(new Date(10000));
        dispatch5.setRecipientData(new Date(11000));
        dispatch5.setWeight(8);
        dispatch5.setValue((14));

        List<Dispatch> dispatchList = new ArrayList<>();
        dispatchList.add(dispatch1);
        dispatchList.add(dispatch2);
        dispatchList.add(dispatch3);
        dispatchList.add(dispatch4);
        dispatchList.add(dispatch5);

        Iterable<Dispatch> dispatchIterable = dispatchRepository.saveAll(dispatchList);
        assertThat(dispatchIterable.spliterator().getExactSizeIfKnown()).isEqualTo(5);

    }




}
