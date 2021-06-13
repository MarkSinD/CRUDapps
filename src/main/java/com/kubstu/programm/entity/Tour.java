package com.kubstu.programm.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;

    private String resort;

    private String transport;

    @Column(name = "visa_service")
    private boolean visaService;

    @Column(name = "accommodation")
    private boolean accommodation;

    @Column(name = "accommodation_type")
    private String accommodationType;

    private boolean power;

    @Column(name = "power_type")
    private String powerType;

    private boolean excursions;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    private String tour;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Serve serve;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private Set<Order> tours = new HashSet<>();


    public Tour() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResort() {
        return resort;
    }

    public void setResort(String resort) {
        this.resort = resort;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public boolean isVisaService() {
        return visaService;
    }

    public void setVisaService(boolean visaService) {
        this.visaService = visaService;
    }

    public boolean isAccommodation() {
        return accommodation;
    }

    public void setAccommodation(boolean accommodation) {
        this.accommodation = accommodation;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public boolean isExcursions() {
        return excursions;
    }

    public void setExcursions(boolean excursions) {
        this.excursions = excursions;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public Serve getServe() {
        return serve;
    }

    public void setServe(Serve serve) {
        this.serve = serve;
    }

    public Set<Order> getTours() {
        return tours;
    }

    public void setTours(Set<Order> tours) {
        this.tours = tours;
    }

    @Override
    public String toString() {
        return country + " " + resort + " " + price;
    }
}
