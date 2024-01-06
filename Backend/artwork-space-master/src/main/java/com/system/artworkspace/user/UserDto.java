package com.system.artworkspace.user;

import com.system.artworkspace.artwork.ArtworkDto;
import jakarta.validation.constraints.*;


import java.util.List;

public class UserDto {
    private Long id;
    @NotBlank(message = "Username name cant be blank")
    @Size(max = 100, message = "Username cant be longer than 100 symbols")
    @NotNull(message = "UserName cant be null")
    @NotBlank(message = "UserName cant be blank")
    private String username;

    @Size(max = 100, message = "Firstname cant be longer than 100 symbols")
    @NotNull(message = "FirstName cant be null")
    @NotBlank(message = "FirstName cant be blank")
    private String firstName;

    @Size(max = 100, message = "Lastname cant be longer than 100 symbols")
    @NotNull(message = "LastName cant be null")
    @NotBlank(message = "LastName cant be blank")
    private String lastName;

    @Email(message = "Email is not valid")
    @Size(max = 100, message = "Email cant be longer than 100 symbols")
    @NotNull(message = "Email cant be null")
    @NotBlank(message = "Email cant be blank")
    private String email;

   @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password need to be minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
   private String password;

    private Role role;

    public UserDto() {
    }

    public UserDto(Long id, String username, String firstName, String lastName, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role=role;
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

}
