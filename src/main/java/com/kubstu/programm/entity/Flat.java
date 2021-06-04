package com.kubstu.programm.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flat")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count_of_rooms")
    private Integer countRooms;

    @Column(name = "living_space")
    private Integer livingSpace;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "flat")
    private Set<Tenant> tenantSet = new HashSet<>();

    public Flat() {
    }


    @Override
    public String toString() {
        return
                "count rooms: " + countRooms +
                "\nliving space: " + livingSpace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(Integer countRooms) {
        this.countRooms = countRooms;
    }

    public Integer getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(Integer livingSpace) {
        this.livingSpace = livingSpace;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Set<Tenant> getTenantSet() {
        return tenantSet;
    }

    public void setTenantSet(Set<Tenant> tenantSet) {
        this.tenantSet = tenantSet;
    }
}
