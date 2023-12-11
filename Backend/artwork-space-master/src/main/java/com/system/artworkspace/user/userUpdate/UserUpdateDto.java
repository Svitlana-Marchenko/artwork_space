package com.system.artworkspace.user.userUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {

    private Long id;
    @NotBlank(message = "Username cant be blank")
    @NotNull(message = "Username name cant be null")
    @Size(max = 100, message = "Username cant be longer than 100 symbols")
    private String username;

    @NotBlank(message = "FirstName name cant be blank")
    @NotNull(message = "FirstName name cant be null")
    @Size(max = 100, message = "Firstname cant be longer than 100 symbols")
    private String firstName;

    @NotBlank(message = "LastName name cant be blank")
    @NotNull(message = "LastName name cant be null")
    @Size(max = 100, message = "Lastname cant be longer than 100 symbols")
    private String lastName;

    public UserUpdateDto(Long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
