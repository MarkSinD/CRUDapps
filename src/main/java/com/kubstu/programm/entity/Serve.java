package com.kubstu.programm.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Serve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "serve")
    private Set<ServicePerformed> performedSet = new HashSet<>();

    public Serve() {
    }

    @Override
    public String toString() {
        return name + ' ' + price + '$';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<ServicePerformed> getPerformedSet() {
        return performedSet;
    }

    public void setPerformedSet(Set<ServicePerformed> performedSet) {
        this.performedSet = performedSet;
    }
}

