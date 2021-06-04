package com.kubstu.programm.entity;

import javax.persistence.*;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@Entity
@Table(name = "tenant_detail")
public class TenantDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "type_of_registration")
    private String typeRegistration;

    public TenantDetail() {

    }

    @Override
    public String toString() {
        return "TenantDetail{" +
                "id=" + id +
                ", tenant=" + tenant +
                ", typeRegistration='" + typeRegistration + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getTypeRegistration() {
        return typeRegistration;
    }

    public void setTypeRegistration(String typeRegistration) {
        this.typeRegistration = typeRegistration;
    }
}
