package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.entity.Receiving;
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
public class ReceivingRepositoryTest {

    @Autowired
    private ReceivingRepository receivingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void emptyMethod() {

    }

    @Test
    public void addOne(){
        Receiving receiving = new Receiving();
        receiving.setTypeReceipt("Post of Russia");
        receiving.setSenderData(new Date(10000));
        receiving.setRecipientDate(new Date(11000));
        receiving.setWeight(15);
        receiving.setValue((10));

        Receiving savedReceiving = receivingRepository.save(receiving);
        assertThat(savedReceiving.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Receiving receiving1 = new Receiving();
        receiving1.setTypeReceipt("DPD");
        receiving1.setSenderData(new Date(10000));
        receiving1.setRecipientDate(new Date(11000));
        receiving1.setWeight(12);
        receiving1.setValue((11));

        Receiving receiving2 = new Receiving();
        receiving2.setTypeReceipt("CDEK");
        receiving2.setSenderData(new Date(10000));
        receiving2.setRecipientDate(new Date(11000));
        receiving2.setWeight(1);
        receiving2.setValue((12));

        Receiving receiving3 = new Receiving();
        receiving3.setTypeReceipt("Boxberry");
        receiving3.setSenderData(new Date(10000));
        receiving3.setRecipientDate(new Date(11000));
        receiving3.setWeight(3);
        receiving3.setValue((5));

        Receiving receiving4 = new Receiving();
        receiving4.setTypeReceipt("Imbirbar");
        receiving4.setSenderData(new Date(10000));
        receiving4.setRecipientDate(new Date(11000));
        receiving4.setWeight(7);
        receiving4.setValue((5));

        Receiving receiving5 = new Receiving();
        receiving5.setTypeReceipt("FARFOR");
        receiving5.setSenderData(new Date(10000));
        receiving5.setRecipientDate(new Date(11000));
        receiving5.setWeight(8);
        receiving5.setValue((14));

        List<Receiving> receivingList = new ArrayList<>();
        receivingList.add(receiving1);
        receivingList.add(receiving2);
        receivingList.add(receiving3);
        receivingList.add(receiving4);
        receivingList.add(receiving5);

        Iterable<Receiving> dispatchIterable = receivingRepository.saveAll(receivingList);
        assertThat(dispatchIterable.spliterator().getExactSizeIfKnown()).isEqualTo(5);

    }
}
