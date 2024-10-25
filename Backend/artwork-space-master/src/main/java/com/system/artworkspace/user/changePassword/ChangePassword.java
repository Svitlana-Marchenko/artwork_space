package com.system.artworkspace.user.changePassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ChangePassword {

    private Long id;

    private String oldPassword;

    private String newPassword;
}
