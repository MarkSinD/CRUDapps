package com.kubstu.programm.repository;

import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.entity.TenantDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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
public class TetantDetailRepositoryTest {

    @Autowired
    private TenantDetailRepository tenantDetailRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOne(){
        Tenant tenant = entityManager.find(Tenant.class, 1);

        TenantDetail tenantDetail = new TenantDetail();
        tenantDetail.setTypeRegistration("constant");
        tenantDetail.setTenant(tenant);
        TenantDetail savedTenantDetail = tenantDetailRepository.save(tenantDetail);
        assertThat(savedTenantDetail.getId()).isNotEqualTo(0);
    }


}
