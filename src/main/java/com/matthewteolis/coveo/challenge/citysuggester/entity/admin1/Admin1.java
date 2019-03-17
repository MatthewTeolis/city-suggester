package com.matthewteolis.coveo.challenge.citysuggester.entity.admin1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin1")
public class Admin1 {

    @Id
    private String code;

    @Column
    private String name;

    public Admin1() {
    }

    Admin1(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
