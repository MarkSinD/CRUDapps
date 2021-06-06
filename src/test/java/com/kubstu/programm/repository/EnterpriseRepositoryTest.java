package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Enterprise;
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
public class EnterpriseRepositoryTest {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testEmptyMethod(){

    }

    @Test
    public void testAddOne(){
        Enterprise enterprise = new Enterprise();
        enterprise.setName("Fire service No. 1");
        enterprise.setAddress("Kransodar");
        enterprise.setTelephone(89003254223L);
        Enterprise entetpriseSaved = enterpriseRepository.save(enterprise);
        assertThat(entetpriseSaved.getId()).isNotEqualTo(0);
    }

    @Test
    public void testAddList(){
        Enterprise enterprise1 = new Enterprise();
        enterprise1.setName("Fire service No. 4");
        enterprise1.setAddress("Tomsk");
        enterprise1.setTelephone(89182341223L);

        Enterprise enterprise2 = new Enterprise();
        enterprise2.setName("Fire service No. 7");
        enterprise2.setAddress("Moskow");
        enterprise2.setTelephone(2546784L);

        Enterprise enterprise3 = new Enterprise();
        enterprise3.setName("Rescue Service No. 2");
        enterprise3.setAddress("Kransodar");
        enterprise3.setTelephone(89893253234L);

        Enterprise enterprise4 = new Enterprise();
        enterprise4.setName("Rescue Service No. 1");
        enterprise4.setAddress("Tomsk");
        enterprise4.setTelephone(89893254223L);

        Enterprise enterprise5 = new Enterprise();
        enterprise5.setName("Fire service No. 1");
        enterprise5.setAddress("Kiev");
        enterprise5.setTelephone(89253254223L);

        List<Enterprise> enterpriseList = new ArrayList<>();
        enterpriseList.add(enterprise1);
        enterpriseList.add(enterprise2);
        enterpriseList.add(enterprise3);
        enterpriseList.add(enterprise4);
        enterpriseList.add(enterprise5);


        Iterable<Enterprise> enterpriseSaved = enterpriseRepository.saveAll(enterpriseList);
        assertThat(enterpriseSaved.spliterator().getExactSizeIfKnown()).isEqualTo(5);
    }

}
