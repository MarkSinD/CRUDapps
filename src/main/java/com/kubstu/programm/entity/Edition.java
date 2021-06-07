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
@Table(name = "edition")
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attribute_name")
    private String attributeName;

    private String name;

    private Integer price;

    @OneToMany(mappedBy = "edition", cascade = CascadeType.ALL)
    private Set<Subscription> editionSet = new HashSet<>();

    public Edition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Subscription> getEditionSet() {
        return editionSet;
    }

    public void setEditionSet(Set<Subscription> editionSet) {
        this.editionSet = editionSet;
    }

    @Override
    public String toString() {
        return name + ", " + price + "$";
    }
}
