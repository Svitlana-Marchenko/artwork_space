package com.system.artworkspace.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

   private Role role;
}
