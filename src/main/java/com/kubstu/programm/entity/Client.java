package com.kubstu.programm.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String patronymic;

    @Column(name = "date_birth")
    private Date dateBitrh;

    private Long telephone;

    private Integer discount;

    @OneToMany(mappedBy = "client")
    private Set<Book> orders = new HashSet<>();

    public Client() {
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateBitrh() {
        return dateBitrh;
    }

    public void setDateBitrh(Date dateBitrh) {
        this.dateBitrh = dateBitrh;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Set<Book> getOrders() {
        return orders;
    }

    public void setOrders(Set<Book> orders) {
        this.orders = orders;
    }
}
