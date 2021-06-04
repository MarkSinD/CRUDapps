package com.kubstu.programm.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "tenant")
    private Set<TenantDetail> tenantDetailSet = new HashSet<>();

    @OneToMany(mappedBy = "tenant")
    private Set<Serve> serveSet = new HashSet<>();

    public Tenant() {
    }

    @Override
    public String toString() {
        return "name: " + name +
                " surname: " + surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Set<TenantDetail> getTenantDetailSet() {
        return tenantDetailSet;
    }

    public void setTenantDetailSet(Set<TenantDetail> tenantDetailSet) {
        this.tenantDetailSet = tenantDetailSet;
    }

    public Set<Serve> getServeSet() {
        return serveSet;
    }

    public void setServeSet(Set<Serve> serveSet) {
        this.serveSet = serveSet;
    }
}
