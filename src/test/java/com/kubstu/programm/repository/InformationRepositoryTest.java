package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Information;
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
public class InformationRepositoryTest {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testEmptyMethod(){

    }

    @Test
    public void addOne(){
        Information citizen1 = new Information();
        citizen1.setName("Anatoliy");
        citizen1.setSurname("Serpov");
        citizen1.setPatronymic("Anatolivich");
        citizen1.setDateBirth(new Date(41231221));
        citizen1.setGender("male");
        citizen1.setAddress("Kransodar");
        citizen1.setTelephone(89002745612L);
        citizen1.setBenefitCode(4567);

        Information citizen2 = new Information();
        citizen2.setName("Anton");
        citizen2.setSurname("Smirnov");
        citizen2.setPatronymic("Antonovich");
        citizen2.setDateBirth(new Date(23521223));
        citizen2.setGender("male");
        citizen2.setAddress("Kransodar");
        citizen2.setTelephone(89452745612L);
        citizen2.setBenefitCode(4567);

        Information citizen3 = new Information();
        citizen3.setName("Arkadiy");
        citizen3.setSurname("Serpov");
        citizen3.setPatronymic("Arkadiyvich");
        citizen3.setDateBirth(new Date(41231221));
        citizen3.setGender("male");
        citizen3.setAddress("Kransodar");
        citizen3.setTelephone(89182745612L);
        citizen3.setBenefitCode(2367);

        Information citizen4 = new Information();
        citizen4.setName("Artur");
        citizen4.setSurname("Visin");
        citizen4.setPatronymic("Arturvich");
        citizen4.setDateBirth(new Date(41231221));
        citizen4.setGender("male");
        citizen4.setAddress("Moscow");
        citizen4.setTelephone(89182745612L);
        citizen4.setBenefitCode(9067);

        List<Information> informationList = new ArrayList<>();
        informationList.add(citizen1);
        informationList.add(citizen2);
        informationList.add(citizen3);
        informationList.add(citizen4);

        Iterable<Information> informationSaved = informationRepository.saveAll(informationList);
        assertThat(informationSaved.spliterator().getExactSizeIfKnown()).isEqualTo(4);

    }

}
