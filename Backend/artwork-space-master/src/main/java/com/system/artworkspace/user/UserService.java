package com.system.artworkspace.user;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.auction.Sale.Sale;
import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(UserUpdate user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    void changePassword(ChangePassword changePassword);

    List<Artwork> getCollectionByUserId(Long id);
    void addArtworkToCollection(Long id, Long artworkId);
    void removeArtworkFromCollection (Long id, Long artworkId);

}

