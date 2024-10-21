package com.system.artworkspace.user.changePassword;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePassword {

    private Long id;

    private String oldPassword;

    private String newPassword;

}
