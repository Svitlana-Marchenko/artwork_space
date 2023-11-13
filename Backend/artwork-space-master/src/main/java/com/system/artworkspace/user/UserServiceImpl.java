package com.system.artworkspace.user;

import com.system.artworkspace.ArtworkSpaceApplication;
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

    @Autowired
    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity createdUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
        logger.info(CONFIDENTIAL_USER_EVENTS,"Created user with ID: {}", user.getId());
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
}
