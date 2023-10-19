package com.system.artworkspace.user;

import com.system.artworkspace.exceptions.NoSuchUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        logger.info("Creating a user with ID: {}", user.getId());
        User createdUser = userService.createUser(UserMapper.INSTANCE.userDtoToUser(user));
        logger.info("User created with ID: {}", createdUser.getId());
        return UserMapper.INSTANCE.userToUserDto(createdUser);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user) {
        logger.info("Updating user with ID: {}", user.getId());
        User updatedUser = userService.updateUser(UserMapper.INSTANCE.userDtoToUser(user));
        logger.info("UserEntity updated with ID: {}", updatedUser.getId());
        return UserMapper.INSTANCE.userToUserDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        logger.info("UserEntity deleted with ID: {}", userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        logger.info("Retrieving user with ID: {}", userId);
        return UserMapper.INSTANCE.userToUserDto(userService.getUserById(userId));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserEntity not found: " + e.getMessage());
    }
}
