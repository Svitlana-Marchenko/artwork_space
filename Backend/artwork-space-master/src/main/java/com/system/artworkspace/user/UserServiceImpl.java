package com.system.artworkspace.user;

import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.exceptions.*;
import com.system.artworkspace.helpers.ImagesManager;
import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_USER_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.USER_ACTIONS;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    
    private final ArtworkRepository artworkRepository;
    
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           ArtworkRepository artworkRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.artworkRepository = artworkRepository;
    }
    
    @Override
    public User createUser(User user) {
        checkNewUsername(user.getUsername());
        checkNewEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity createdUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
        log.info(CONFIDENTIAL_USER_EVENTS,"Created user with ID: {}", createdUser.getId());
        if(user.getRole().equals(Role.ARTIST))
            ImagesManager.createFolderForArtist(createdUser.getId());
        return UserMapper.INSTANCE.userEntityToUser(createdUser);
    }

    @Override
    public User updateUser(UserUpdate user) {
        Optional<UserEntity> userO = userRepository.findById(user.getId());
        if(userO.isPresent()){
            User u = UserMapper.INSTANCE.userEntityToUser(userO.get());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            if(!u.getUsername().equals(user.getUsername())){
                checkNewUsername(user.getUsername());
            }
            u.setUsername(user.getUsername());
            UserEntity updatedUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(u));
            log.info(USER_ACTIONS,"Updated user with ID: {}", updatedUser.getId());
            return UserMapper.INSTANCE.userEntityToUser(updatedUser);
        }
        else{
            throw new NoSuchUserException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            artworkRepository.deleteArtworksByUserId(userId);
            userRepository.deleteById(userId);
            log.info(USER_ACTIONS,"Deleted user with ID: {}", userId);
        } else {
            log.warn(USER_ACTIONS,"UserEntity not found for deletion with ID: {}", userId);
        }
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.debug(USER_ACTIONS,"Retrieved user with ID: {}", user.getId());
        } else {
            log.warn(USER_ACTIONS,"UserEntity not found with ID: {}", userId);
            throw new NoSuchUserException("User with id " + userId + " not found");
        }
        return UserMapper.INSTANCE.userEntityToUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(UserMapper.INSTANCE::userEntityToUser).orElse(null);
    }

    @Override
    public void changePassword(ChangePassword changePassword) {
        Optional<UserEntity> userO = userRepository.findById(changePassword.getId());
        if(userO.isPresent()){
            User user = UserMapper.INSTANCE.userEntityToUser(userO.get());
            checkOldPassword(changePassword.getOldPassword(), user.getPassword());
            checkNewPasswordFormat(changePassword.getNewPassword());
            setNewPassword(user, changePassword.getNewPassword());
        } else {
            log.warn(USER_ACTIONS,"UserEntity not found with ID: {}", changePassword.getId());
            throw new NoSuchUserException("User with id " + changePassword.getId() + " not found");
        }
    }


    private void checkOldPassword(String oldPassword, String passwordInDb) {
        if (!passwordEncoder.matches(oldPassword, passwordInDb))
            throw new InvalidOldPasswordException("Invalid old password");
    }

    private void checkNewPasswordFormat(String password) {
        if(password.length() < 8)
            throw new WrongPasswordFormat("Password length is less than 8 symbols.");
    }

    private void checkNewUsername(String username) {
        if(userRepository.findByUsername(username).isPresent())
            throw new InvalidUserDataException("This username is already exist");
    }

    private void checkNewEmail(String email) {
        if(userRepository.findByEmail(email).isPresent())
            throw new InvalidUserDataException("This email is already exist");
    }

    private void setNewPassword(User user, String newPassword) {
        String encryptedNewPassword = encryptPassword(newPassword);
        user.setPassword(encryptedNewPassword);
        userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
    }

    private String encryptPassword(String newPassword) {
        return passwordEncoder.encode(newPassword);
    }
}
