package com.eagulyi.user.entity;

import javax.persistence.*;

/**
 * Created by eugene on 5/26/17.
 */
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    public Country() {
    }

    public Country(String country) {
        this.name = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
