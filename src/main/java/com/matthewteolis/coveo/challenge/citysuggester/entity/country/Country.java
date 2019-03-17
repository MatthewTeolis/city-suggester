package com.matthewteolis.coveo.challenge.citysuggester.entity.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    private String ISO;

    @Column
    private String name;

    public Country() {
    }

    Country(String ISO, String name) {
        this.ISO = ISO;
        this.name = name;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
