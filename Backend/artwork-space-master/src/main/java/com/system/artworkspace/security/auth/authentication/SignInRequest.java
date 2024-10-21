package com.system.artworkspace.security.auth.authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequest {

    private String username;

    private String password;

}
