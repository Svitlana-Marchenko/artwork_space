package com.system.artworkspace.user;

import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;

public interface UserService {

    User createUser(User user);

    User updateUser(UserUpdate user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    void changePassword(ChangePassword changePassword);
}

