package com.kubstu.programm.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "administration")
public class Administration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    private Integer postcode;

    private String email;

    private Long telephone;

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    private Set<Enterprise> enterpriseSet = new HashSet<>();

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    private Set<Executive> executiveSet = new HashSet<>();

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    private Set<Information> informationSet = new HashSet<>();

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    private Set<Land> landSet = new HashSet<>();

    public Administration() {
    }

    @Override
    public String toString() {
        return name + ", " + address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Set<Enterprise> getEnterpriseSet() {
        return enterpriseSet;
    }

    public void setEnterpriseSet(Set<Enterprise> enterpriseSet) {
        this.enterpriseSet = enterpriseSet;
    }

    public Set<Executive> getExecutiveSet() {
        return executiveSet;
    }

    public void setExecutiveSet(Set<Executive> executiveSet) {
        this.executiveSet = executiveSet;
    }

    public Set<Information> getInformationSet() {
        return informationSet;
    }

    public void setInformationSet(Set<Information> informationSet) {
        this.informationSet = informationSet;
    }

    public Set<Land> getLandSet() {
        return landSet;
    }

    public void setLandSet(Set<Land> landSet) {
        this.landSet = landSet;
    }

}
