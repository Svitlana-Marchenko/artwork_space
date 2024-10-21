package com.system.artworkspace.user.userUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserUpdate {

    private long id;

    private String username;

    private String firstName;

    private String lastName;
}
