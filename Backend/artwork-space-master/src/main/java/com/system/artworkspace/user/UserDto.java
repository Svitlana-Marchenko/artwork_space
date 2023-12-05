package com.system.artworkspace.user;

import com.system.artworkspace.artwork.ArtworkDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {
    private Long id;
    @NotNull
    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String password;

    private Role role;

    private List<ArtworkDto> collection;
    public UserDto() {
    }

    public UserDto(Long id, String username, String firstName, String lastName, String email, String password, Role role, List<ArtworkDto> collection) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role=role;
        this.collection=collection;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ArtworkDto> getCollection() {
        return collection;
    }

    public void setCollection(List<ArtworkDto> collection) {
        this.collection = collection;
    }
}
