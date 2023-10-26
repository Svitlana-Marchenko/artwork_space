package com.system.artworkspace;

import com.system.artworkspace.artwork.ArtworkDto;
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
       // userService.createUser(new User(123L,"aa","cc","dd","kk","kk",12L));

        ArtworkServiceImpl artworkService = new ArtworkServiceImpl(null);
        ArtworkDto artwork = new ArtworkDto(5050L,"t","desc","tech",100,200,3434L,"url",12);
        //artworkService.addArtwork(artwork);
        //artworkService.updateTitle(artwork,"kk");
        //artworkService.updateTechnique(artwork, "uuuuuuu");
        //artworkService.updateImgUrl(artwork,"test");
    }
}

