package com.system.artworkspace.service;

import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.exceptions.*;
import com.system.artworkspace.user.*;
import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;
import com.system.artworkspace.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.system.artworkspace.utils.Utils.getRandomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateUser_whenValidUserProvided() {
        User user = UserUtils.getRandomUser();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPassword("password123");

        UserEntity userEntity = UserUtils.getRandomUserEntity();
        userEntity.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void shouldThrowException_whenUsernameExistsOnCreateUser() {
        User user = UserUtils.getRandomUser();
        user.setUsername("existinguser");
        user.setEmail("newuser@example.com");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(UserUtils.getRandomUserEntity()));

        assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).findByUsername("existinguser");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void shouldThrowException_whenEmailExistsOnCreateUser() {
        User user = UserUtils.getRandomUser();
        user.setUsername("newuser");
        user.setEmail("existingemail@example.com");

        when(userRepository.findByEmail("existingemail@example.com")).thenReturn(Optional.of(UserUtils.getRandomUserEntity()));

        assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).findByEmail("existingemail@example.com");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void shouldUpdateUser_whenValidUserUpdateProvided() {
        UserUpdate userUpdate = UserUtils.getRandomUserUpdate();
        userUpdate.setId(1L);
        userUpdate.setUsername(getRandomString());

        UserEntity userEntity = UserUtils.getRandomUserEntity();
        userEntity.setId(1L);
        userEntity.setUsername(getRandomString());

        UserEntity updatedUserEntity = UserUtils.getRandomUserEntity();
        updatedUserEntity.setUsername(userUpdate.getUsername());

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        User updatedUser = userService.updateUser(userUpdate);

        assertEquals(userUpdate.getUsername(), updatedUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void shouldThrowException_whenUpdatingNonExistentUser() {
        UserUpdate userUpdate = UserUtils.getRandomUserUpdate();
        userUpdate.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.updateUser(userUpdate));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void shouldDeleteUser_whenUserExists() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(artworkRepository, times(1)).deleteArtworksByUserId(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void shouldNotDeleteUser_whenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        userService.deleteUser(userId);

        verify(userRepository, never()).deleteById(userId);
        verify(artworkRepository, never()).deleteArtworksByUserId(userId);
    }

    @Test
    public void shouldChangePassword_whenValidChangePasswordProvided() {
        ChangePassword changePassword = UserUtils.getRandomChangePassword();
        changePassword.setId(1L);
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("newPassword123");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedOldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(changePassword.getOldPassword(), userEntity.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(changePassword.getNewPassword())).thenReturn("encodedNewPassword");

        userService.changePassword(changePassword);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void shouldThrowException_whenOldPasswordIsIncorrect() {
        ChangePassword changePassword = UserUtils.getRandomChangePassword();
        changePassword.setId(1L);
        changePassword.setOldPassword("wrongOldPassword");
        changePassword.setNewPassword("newPassword123");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedOldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(changePassword.getOldPassword(), userEntity.getPassword())).thenReturn(false);

        assertThrows(InvalidOldPasswordException.class, () -> userService.changePassword(changePassword));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void shouldThrowException_whenNewPasswordIsTooShort() {
        ChangePassword changePassword = UserUtils.getRandomChangePassword();
        changePassword.setId(1L);
        changePassword.setOldPassword("oldPassword");
        changePassword.setNewPassword("short");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedOldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(changePassword.getOldPassword(), userEntity.getPassword())).thenReturn(true);

        assertThrows(WrongPasswordFormat.class, () -> userService.changePassword(changePassword));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void shouldReturnUserById_whenUserExists() {
        Long userId = 1L;
        UserEntity userEntity = UserUtils.getRandomUserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        User user = userService.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void shouldThrowException_whenUserNotFoundById() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void shouldReturnUserByUsername_whenUserExists() {
        String username = "testuser";
        UserEntity userEntity = UserUtils.getRandomUserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        User user = userService.getUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void shouldReturnNull_whenUserNotFoundByUsername() {
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        User user = userService.getUserByUsername(username);

        assertNull(user);
        verify(userRepository, times(1)).findByUsername(username);
    }
}

