package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Administration;
import com.kubstu.programm.entity.Enterprise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AdministrationRepositoryTest {

    @Autowired
    private AdministrationRepository administrationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testEmptyMethod(){

    }

    @Test
    public void addOne(){
        Administration administration = new Administration();
        administration.setName("Ministry of Emergency Situations of the Krasnodar Territory");
        administration.setAddress("123 Krasnaya Street");
        administration.setPostcode(458789);
        administration.setEmail("ministry-krd@mail.ru");
        administration.setTelephone(2567274L);
        Administration savedAdministration = administrationRepository.save(administration);
        assertThat(savedAdministration.getId()).isNotEqualTo(0);
    }

    @Test
    public void addListAdministrations(){
        Administration administration1 = new Administration();
        administration1.setName("Ministry of Emergency Situations of the Moscow Territory");
        administration1.setAddress("456 Lenin street");
        administration1.setPostcode(152789);
        administration1.setEmail("ministry-msc@mail.ru");
        administration1.setTelephone(2112222L);

        Administration administration2 = new Administration();
        administration2.setName("Ministry of Emergency Situations of the Tomsk Territory");
        administration2.setAddress("453 Rolist street");
        administration2.setPostcode(235211);
        administration2.setEmail("ministry-tomsk@mail.ru");
        administration2.setTelephone(2456822L);

        Administration administration3 = new Administration();
        administration3.setName("Ministry of Emergency Situations of the Kiev Territory");
        administration3.setAddress("453 Protil street");
        administration3.setPostcode(413331);
        administration3.setEmail("ministry-tomsk@mail.ru");
        administration3.setTelephone(2354412L);

        List<Administration> administrationList = new ArrayList<>();
        administrationList.add(administration1);
        administrationList.add(administration2);
        administrationList.add(administration3);

        Iterable<Administration> administrationSaved = administrationRepository.saveAll(administrationList);
        assertThat(administrationSaved.spliterator().getExactSizeIfKnown()).isEqualTo(3);
    }

    @Test
    public void showOneAdministration(){
        Administration administration = testEntityManager.find(Administration.class, 1);
        Set<Enterprise> enterpriseSet = administration.getEnterpriseSet();
        System.out.println(enterpriseSet);
    }

}
