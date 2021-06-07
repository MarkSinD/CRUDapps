package com.kubstu.programm.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subscription_Date")
    private Date subscriptionDate;

    @Column(name = "subscription_term")
    private Integer subscriptionTerm;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition edition;

    public Subscription() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Integer getSubscriptionTerm() {
        return subscriptionTerm;
    }

    public void setSubscriptionTerm(Integer subscriptionTerm) {
        this.subscriptionTerm = subscriptionTerm;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Date=" + subscriptionDate +
                ", Term=" + subscriptionTerm;
    }
}
