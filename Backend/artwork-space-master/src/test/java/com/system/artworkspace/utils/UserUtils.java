package com.system.artworkspace.utils;

import com.system.artworkspace.user.Role;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.changePassword.ChangePassword;
import com.system.artworkspace.user.userUpdate.UserUpdate;

import static com.system.artworkspace.utils.Utils.getRandomLong;
import static com.system.artworkspace.utils.Utils.getRandomString;

public class UserUtils {

    public static UserEntity getRandomUserEntity() {
        return new UserEntity(getRandomLong(), getRandomString(), getRandomString(), getRandomString(), getRandomString(), getRandomString(), Role.ARTIST);
    }

    public static User getRandomUser() {
        return new User(getRandomLong(), getRandomString(), getRandomString(), getRandomString(), getRandomString(), getRandomString(), Role.ARTIST);
    }

    public static UserUpdate getRandomUserUpdate() {
        return new UserUpdate(getRandomLong(), getRandomString(), getRandomString(), getRandomString());
    }

    public static ChangePassword getRandomChangePassword() {
        return new ChangePassword(getRandomLong(), getRandomString(), getRandomString());
    }

}
