package com.system.artworkspace.user;

import com.system.artworkspace.user.changePassword.ChangePassword;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    void changePassword(ChangePassword changePassword);
}

