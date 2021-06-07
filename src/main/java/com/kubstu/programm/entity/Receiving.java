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
@Table(name = "receiving")
public class Receiving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_of_receipt")
    private String typeReceipt;

    @Column(name = "sender_date")
    private Date senderData;

    @Column(name = "recipient_date")
    private Date recipientDate;

    private Integer weight;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;

    public Receiving() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeReceipt() {
        return typeReceipt;
    }

    public void setTypeReceipt(String typeReceipt) {
        this.typeReceipt = typeReceipt;
    }

    public Date getSenderData() {
        return senderData;
    }

    public void setSenderData(Date senderData) {
        this.senderData = senderData;
    }

    public Date getRecipientDate() {
        return recipientDate;
    }

    public void setRecipientDate(Date recipientDate) {
        this.recipientDate = recipientDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
