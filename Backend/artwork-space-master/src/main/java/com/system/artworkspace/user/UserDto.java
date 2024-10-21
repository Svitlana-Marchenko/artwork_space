package com.system.artworkspace.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
