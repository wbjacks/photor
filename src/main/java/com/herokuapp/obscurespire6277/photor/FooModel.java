package com.herokuapp.obscurespire6277.photor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FooModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;

    FooModel(){}

    public FooModel(String name) {
        this.name = name;
    }
}
