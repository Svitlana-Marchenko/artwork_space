package com.system.artworkspace.user.userUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {

    private long id;

    private String username;

    private String firstName;

    private String lastName;
}
