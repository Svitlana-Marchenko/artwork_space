package com.system.artworkspace.user;

import com.system.artworkspace.ArtworkSpaceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Override
    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        logger.info("Created user with ID: {}", createdUser.getId());
        return createdUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        logger.info("Updated user with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            logger.info("Deleted user with ID: {}", userId);
        } else {
            logger.warn("User not found for deletion with ID: {}", userId);
        }
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            logger.info("Retrieved user with ID: {}", user.getId());
        } else {
            logger.warn("User not found with ID: {}", userId);
        }
        return user;
    }
}
