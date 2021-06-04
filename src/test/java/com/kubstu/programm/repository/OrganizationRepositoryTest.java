package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Organization organization = new Organization();
        organization.setName("name of organization");
        organization.setAddress("Krasnodar");
        organization.setTelephone("2737456");
        Organization savedOrganization = organizationRepository.save(organization);
        assertThat(savedOrganization.getId()).isNotEqualTo(0);
    }

    @Test
    public void addListOrganization(){
        Organization o1 = new Organization();
        o1.setName("RTN");
        o1.setAddress("Kiew");
        o1.setTelephone("2737456");

        Organization o2 = new Organization();
        o2.setName("TTN");
        o2.setAddress("Moskow");
        o2.setTelephone("2737456");

        Organization o3 = new Organization();
        o3.setName("MNP");
        o3.setAddress("Vladimir");
        o3.setTelephone("2737456");

        Organization o4 = new Organization();
        o4.setName("RMN");
        o4.setAddress("Timashevsk");
        o4.setTelephone("2737456");

        Organization o5 = new Organization();
        o5.setName("PTV");
        o5.setAddress("Moskow");
        o5.setTelephone("2737456");

        Organization o6 = new Organization();
        o6.setName("MWQ");
        o6.setAddress("Moskow");
        o6.setTelephone("2737456");

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(o1);
        organizationList.add(o2);
        organizationList.add(o3);
        organizationList.add(o4);
        organizationList.add(o5);
        organizationList.add(o6);

        List<Organization> organizationsSaved = (List<Organization>) organizationRepository.saveAll(organizationList);
        assertThat(organizationsSaved.size()).isEqualTo(6);
    }

}
