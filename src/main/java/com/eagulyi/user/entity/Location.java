
package com.eagulyi.user.entity;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;


@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    public Location() {
    }

    public Location(City city, Country country) {
        this.city = city;
        this.country = country;
    }

    public Location(String city, String country) {
        this.city = new City(city);
        this.country = new Country(country);
    }

    public Location(String city) {
        this.city = new City(city);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
