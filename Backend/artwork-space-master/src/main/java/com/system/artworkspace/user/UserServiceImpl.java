package com.system.artworkspace.user;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.auction.Sale.Sale;
import com.system.artworkspace.auction.Sale.SaleRepository;
import com.system.artworkspace.exceptions.*;
import com.system.artworkspace.helpers.ImagesManager;
import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_USER_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.USER_ACTIONS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    private ArtworkRepository artworkRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ArtworkRepository artworkRepository) {
        this.userRepository = userRepository;
        this.artworkRepository = artworkRepository;
    }
    @Override
    public User createUser(User user) {
        checkNewUsername(user.getUsername());
        checkNewEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getCollection() == null){
            user.setCollection(new ArrayList<Artwork>());
        }
        UserEntity createdUser = userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
        logger.info(CONFIDENTIAL_USER_EVENTS,"Created user with ID: {}", createdUser.getId());
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
            logger.info(USER_ACTIONS,"Updated user with ID: {}", updatedUser.getId());
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

    @Override
    public List<Artwork> getCollectionByUserId(Long id) {
        logger.info("Getting user with id "+id);
        Optional<UserEntity> userO = userRepository.findById(id);
        if(!userO.isPresent()){
            throw new NoSuchUserException("UserEntity not found with ID: "+id);
        }

        User user = UserMapper.INSTANCE.userEntityToUser(userO.get());
        if(user.getRole()==Role.ARTIST){
            throw new RuntimeException("Artist cant have collection");
        }
        return user.getCollection();
    }

    @Override
    public void addArtworkToCollection(Long id, Long artworkId) {
        logger.info("Getting user with id "+id);
        Optional<UserEntity> userO = userRepository.findById(id);
        if(!userO.isPresent()){
            throw new NoSuchUserException("UserEntity not found with ID: "+id);
        }
        User user = UserMapper.INSTANCE.userEntityToUser(userO.get());
        if(user.getRole()==Role.ARTIST){
            throw new RuntimeException("Artist cant have collection");
        }
        logger.info("Adding artwork with id "+artworkId+" from user`s with id "+ id+" collection");

        Optional<ArtworkEntity> artworkO = artworkRepository.findById(artworkId);
        if(!artworkO.isPresent()){
            throw new NoSuchUserException("ArtworkEntity not found with ID: "+artworkId);
        }
        Artwork artwork = ArtworkMapper.INSTANCE.artworkEntityToArtwork(artworkO.get());

        if(user.getCollection().contains(artwork)){
            return;
        }
        user.getCollection().add(artwork);
        userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
    }

    //todo normal error
    @Override
    public void removeArtworkFromCollection(Long id, Long artworkId) {
        logger.info("Getting user with id "+id);
        Optional<UserEntity> userO = userRepository.findById(id);
        if(!userO.isPresent()){
            throw new NoSuchUserException("UserEntity not found with ID: "+id);
        }
        User user = UserMapper.INSTANCE.userEntityToUser(userO.get());
        if(user.getRole()==Role.ARTIST){
            throw new RuntimeException("Artist cant have collection");
        }
        logger.info("Removing artwork with id "+artworkId+" from user`s with id "+ id+" collection");

        user.getCollection().removeIf((x) -> x.getId().equals(artworkId));
        userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
    }


    private void checkOldPassword(String oldPassword, String passwordInDb) {
        if (!passwordEncoder.matches(oldPassword, passwordInDb))
            throw new InvalidOldPasswordException("Invalid old password");
    }

    private void checkNewPasswordFormat(String password) {
        //todo add regex for password
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
