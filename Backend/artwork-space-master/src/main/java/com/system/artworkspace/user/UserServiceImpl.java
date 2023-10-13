package com.system.artworkspace.user;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.logger.LoggingMarkers;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.system.artworkspace.logger.LoggingMarkers.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Override
    public UserDto createUser(UserDto user) {
        //User createdUser = userRepository.save(user);
        logger.info(CONFIDENTIAL_USER_EVENTS,"Created user with ID: {}", user.getId());
        return user;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User updatedUser = userRepository.save(user.convertToUser());
        logger.info(USER_ACTIONS,"Updated user with ID: {}", updatedUser.getId());
        return updatedUser.convertToUserDto();
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            logger.info(USER_ACTIONS,"Deleted user with ID: {}", userId);
        } else {
            logger.warn(USER_ACTIONS,"User not found for deletion with ID: {}", userId);
        }
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            logger.info(USER_ACTIONS,"Retrieved user with ID: {}", user.getId());
        } else {
            logger.warn(USER_ACTIONS,"User not found with ID: {}", userId);
        }
        return user.convertToUserDto();
    }
}
