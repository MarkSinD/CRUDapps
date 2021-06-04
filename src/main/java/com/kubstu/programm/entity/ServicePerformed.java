package com.kubstu.programm.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "service_performed")
public class ServicePerformed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_service")
    private Date dateService;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Serve serve;

    public ServicePerformed() {
    }

    @Override
    public String toString() {
        return "ServicePerformed{" +
                "id=" + id +
                ", dateService=" + dateService +
                ", service=" + serve +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateService() {
        return dateService;
    }

    public void setDateService(Date dateService) {
        this.dateService = dateService;
    }

    public Serve getServe() {
        return serve;
    }

    public void setServe(Serve serve) {
        this.serve = serve;
    }
}
