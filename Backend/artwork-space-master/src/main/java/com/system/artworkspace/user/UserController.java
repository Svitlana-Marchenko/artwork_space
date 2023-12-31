package com.system.artworkspace.user;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.*;
import com.system.artworkspace.user.changePassword.ChangePasswordDTO;
import com.system.artworkspace.user.changePassword.ChangePasswordMapper;
import javax.validation.Valid;

import com.system.artworkspace.user.userUpdate.UserUpdate;
import com.system.artworkspace.user.userUpdate.UserUpdateDto;
import com.system.artworkspace.user.userUpdate.UserUpdateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public UserDto createUser(@RequestBody @Valid UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Creating a user with ID: {}", user.getId());
        User createdUser = userService.createUser(UserMapper.INSTANCE.userDtoToUser(user));
        logger.info("User created with ID: {}", createdUser.getId());
        return UserMapper.INSTANCE.userToUserDto(createdUser);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody @Valid UserUpdateDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Updating user with ID: {}", user.getId());
        User updatedUser = userService.updateUser(UserUpdateMapper.INSTANCE.userUpdateDtoToUserUpdate(user));
        logger.info("UserEntity updated with ID: {}", updatedUser.getId());
        return UserMapper.INSTANCE.userToUserDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("UserEntity deleted with ID: {}", id);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        logger.info("Retrieving user with ID: {}", userId);
        return UserMapper.INSTANCE.userToUserDto(userService.getUserById(userId));
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDTO request) {
        try {
            userService.changePassword(ChangePasswordMapper.INSTANCE.changePasswordDTOToChangePassword(request));
            return ResponseEntity.ok("The password was changed successfully");
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (InvalidOldPasswordException e) {
            e.printStackTrace();
            String errorCode = e.getErrorCode();
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorCode + ": " + errorMessage);
        } catch (WrongPasswordFormat e) {
            e.printStackTrace();
            String errorCode = e.getErrorCode();
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorCode + ": " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не вдалося змінити пароль.");
        }
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserEntity not found: " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
