package com.system.artworkspace.user;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.exceptions.InvalidOldPasswordException;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.exceptions.NoSuchUserException;
import com.system.artworkspace.exceptions.WrongPasswordFormat;
import com.system.artworkspace.exhibition.ExhibitionRepository;
import com.system.artworkspace.user.changePassword.ChangePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_USER_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.USER_ACTIONS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity createdUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
        logger.info(CONFIDENTIAL_USER_EVENTS,"Created user with ID: {}", createdUser.getId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        UserEntity updatedUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
        logger.info(USER_ACTIONS,"Updated user with ID: {}", updatedUser.getId());
        return UserMapper.INSTANCE.userEntityToUser(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            logger.info(USER_ACTIONS,"Deleted user with ID: {}", userId);
        } else {
            logger.warn(USER_ACTIONS,"UserEntity not found for deletion with ID: {}", userId);
        }
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            logger.info(USER_ACTIONS,"Retrieved user with ID: {}", user.getId());
        } else {
            logger.warn(USER_ACTIONS,"UserEntity not found with ID: {}", userId);
            throw new NoSuchUserException("User with id " + userId + " not found");
        }
        return UserMapper.INSTANCE.userEntityToUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return UserMapper.INSTANCE.userEntityToUser(user.get());
        }
        return null;
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
            logger.warn(USER_ACTIONS,"UserEntity not found with ID: {}", changePassword.getId());
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

    private void setNewPassword(User user, String newPassword) {
        String encryptedNewPassword = encryptPassword(newPassword);
        user.setPassword(encryptedNewPassword);
        userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
    }

    private String encryptPassword(String newPassword) {
        return passwordEncoder.encode(newPassword);
    }
}
