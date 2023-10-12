package com.system.artworkspace;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkServiceImpl;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogDemo.class);
        logger.info("demo");
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUser(new User("aa","cc","dd","kk","kk"));

        ArtworkServiceImpl artworkService = new ArtworkServiceImpl(null);
        Artwork artwork = new Artwork(762971L);

        artworkService.updateTitle(artwork,"kk");
        logger.warn("warning");
    }
}

