package com.kubstu.programm.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    @Column(name = "count_of_apartments")
    private Integer countApartments;

    @ManyToOne
    @JoinColumn(name = "organzation_id")
    private Organization organization;

    @OneToMany(mappedBy = "house")
    private Set<Flat> flatSet = new HashSet<>();

    @OneToMany(mappedBy = "house")
    private Set<Tenant> tenantSet = new HashSet<>();

    public House() {
    }

    @Override
    public String toString() {
        return address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCountApartments() {
        return countApartments;
    }

    public void setCountApartments(Integer countApartments) {
        this.countApartments = countApartments;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Flat> getFlatSet() {
        return flatSet;
    }

    public void setFlatSet(Set<Flat> flatSet) {
        this.flatSet = flatSet;
    }
}
