package com.kubstu.programm.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private String patronymic;
    private String address;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private Set<Subscription> subscriptionSet = new HashSet<>();

    public Subscriber() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Subscription> getSubscriptionSet() {
        return subscriptionSet;
    }

    public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
        this.subscriptionSet = subscriptionSet;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + patronymic;
    }
}


