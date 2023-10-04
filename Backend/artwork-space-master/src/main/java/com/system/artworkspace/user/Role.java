package com.system.artworkspace.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }


    public String getName() {
        return name;
    }
}