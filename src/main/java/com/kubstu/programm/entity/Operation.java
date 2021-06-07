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
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL)
    private Set<Subscription> subscriptionSet = new HashSet<>();

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL)
    private Set<Dispatch> dispatchSet = new HashSet<>();

    public Operation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Subscription> getSubscriptionSet() {
        return subscriptionSet;
    }

    public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
        this.subscriptionSet = subscriptionSet;
    }

    public Set<Dispatch> getDispatchSet() {
        return dispatchSet;
    }

    public void setDispatchSet(Set<Dispatch> dispatchSet) {
        this.dispatchSet = dispatchSet;
    }

    @Override
    public String toString() {
        return type;
    }
}
