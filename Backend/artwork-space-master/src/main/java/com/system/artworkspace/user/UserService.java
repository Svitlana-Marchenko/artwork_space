package com.system.artworkspace.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void registerUser(User user);
    public User getUserByEmail(String email);
}
