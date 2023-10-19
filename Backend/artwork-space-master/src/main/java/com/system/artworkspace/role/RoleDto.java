package com.system.artworkspace.role;

public class RoleDto {
    private Long id;

    private String name;

    public RoleDto() { }

    public RoleDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
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
