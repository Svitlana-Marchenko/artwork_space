package com.system.artworkspace;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkService;
import com.system.artworkspace.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    UserRepository repo;
    @Autowired
    UserService userService;

    @Autowired
    ArtworkService artworkService;
    @Override
    public void run(String... args) {
        createTestData();

    }


    private void createTestData() {
        UserEntity u1 = new UserEntity("art", "John", "Doe", "john.doe@example.com", "password", Role.ARTIST);
        UserEntity u2 = new UserEntity("col", "Anton", "Doe", "anton.doe@example.com", "password", Role.COLLECTIONEER);
        UserEntity u3 = new UserEntity("cur", "Alex", "Doe", "alex.doe@example.com", "password", Role.CURATOR);

        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u1));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u2));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u3));

        ArtworkEntity a1 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "images/artwork.jpeg", 250.0);
        ArtworkEntity a2 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 100.0, "images/artwork1.jpeg", 250.0);
        ArtworkEntity a3 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "images/artwork2.jpeg", 250.0);
        ArtworkEntity a4 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "images/artwork3.jpeg", 250.0);


        artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a1));
        artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a2));
        artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a3));
        artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a4));
    }


}

