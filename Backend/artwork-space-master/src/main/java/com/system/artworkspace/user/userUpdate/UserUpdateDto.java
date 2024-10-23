package com.system.artworkspace.user.userUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
}
