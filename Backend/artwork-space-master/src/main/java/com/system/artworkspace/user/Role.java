package com.system.artworkspace.user;
public class Role {
    private Integer id;

    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}