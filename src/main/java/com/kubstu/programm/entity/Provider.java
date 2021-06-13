package com.kubstu.programm.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_of_representative")
    private String name;

    @Column(name = "surname_of_representative")
    private String surname;

    @Column(name = "telephone_number")
    private Long telephone;

    private String address;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private Set<Serve> serves = new HashSet<>();

    public Provider() {
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

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Serve> getServes() {
        return serves;
    }

    public void setServes(Set<Serve> serves) {
        this.serves = serves;
    }
}
