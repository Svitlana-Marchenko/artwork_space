package com.system.artworkspace.security;

import com.system.artworkspace.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserInfoUserDetails implements UserDetails {
    private UserEntity user;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(UserEntity user) {
        name=user.getUsername();
        this.user = user;
        password=user.getPassword();
        authorities= Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
   System.out.println(authorities.get(0));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserEntity getUser() {
        return user;
    }
}
