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
@Table(name = "dispatch")
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sending_type")
    private String sendingType;

    @Column(name = "sender_date")
    private Date senderDate;

    @Column(name = "recipient_date")
    private Date recipientData;

    private Integer weight;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;

    public Dispatch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSendingType() {
        return sendingType;
    }

    public void setSendingType(String sendingType) {
        this.sendingType = sendingType;
    }

    public Date getSenderDate() {
        return senderDate;
    }

    public void setSenderDate(Date senderDate) {
        this.senderDate = senderDate;
    }

    public Date getRecipientData() {
        return recipientData;
    }

    public void setRecipientData(Date recipientData) {
        this.recipientData = recipientData;
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
