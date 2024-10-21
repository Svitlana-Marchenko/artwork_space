package com.system.artworkspace.user.changePassword;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordDTO {

    private Long id;

    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password need to be minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    private String newPassword;
}
