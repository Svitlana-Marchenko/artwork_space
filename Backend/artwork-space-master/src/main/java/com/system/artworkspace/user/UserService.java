package com.system.artworkspace.user;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);
}

