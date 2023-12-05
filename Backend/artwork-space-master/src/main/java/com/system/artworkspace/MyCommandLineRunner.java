package com.system.artworkspace;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkService;
import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionArtistService;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionMapper;
import com.system.artworkspace.collection.CollectionEntity;
import com.system.artworkspace.collection.CollectionMapper;
import com.system.artworkspace.collection.CollectionService;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import com.system.artworkspace.exhibition.ExhibitionService;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    UserRepository repo;


    @Autowired
    UserService userService;
    @Autowired
    CollectionService collectionService;

    @Autowired
    ExhibitionService exhibitionService;

    @Autowired
    ArtworkService artworkService;
    @Autowired
    AuctionArtistService auctionArtistService;
    @Override
    public void run(String... args) {
        createTestData();

    }


    @SuppressWarnings("deprecation")
    private void createTestData() {
        UserEntity u1 = new UserEntity("art", "John", "Doe", "john.doe@example.com", "password", Role.ARTIST);
        UserEntity u2 = new UserEntity("col", "Anton", "Doe", "anton.doe@example.com", "password", Role.COLLECTIONEER);
        UserEntity u3 = new UserEntity("cur", "Alex", "Doe", "alex.doe@example.com", "password", Role.CURATOR);

        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u1));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u2));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u3));

        List<ArtworkEntity> artworks = new ArrayList<>();

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/flowers.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 100.0, "../images/1.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../images/2.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../images/3.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/4.jpg", 250.0, new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/flowers.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 100.0, "../images/1.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../images/2.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../images/3.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/4.jpg", 250.0, new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/flowers.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 100.0, "../images/1.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../images/2.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../images/3.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/4.jpg", 250.0, new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../images/flowers.jpg", 250.0, new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 100.0, "../images/1.jpg", 250.0, new ArrayList<>()));


        for (ArtworkEntity a: artworks){
            artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a));
        }

        AuctionEntity aa1 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(1L)),  0.0, 10, new Date(), null, 0);

        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa1));

        CollectionEntity collectionEntity1 = new CollectionEntity("Coll1",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
        CollectionEntity collectionEntity2 = new CollectionEntity("Coll2",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collectionEntity1));
        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collectionEntity2));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate1 = null;
        Date endDate1 = null;
        Date startDate2 = null;
        Date endDate2 = null;

        try {
            startDate1 = dateFormat.parse("2023-01-23");
            endDate1 = dateFormat.parse("2024-02-23");
            startDate2 = dateFormat.parse("2023-10-23");
            endDate2 = dateFormat.parse("2023-11-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ExhibitionEntity exhibition1 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),
                "Ex1",
                "Description",
                new LinkedList<ArtworkEntity>(),
                startDate1,
                endDate1
        );

        ExhibitionEntity exhibition2 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),
                "Ex2",
                "Description",
                new LinkedList<ArtworkEntity>(),
                startDate2,
                endDate2
        );

        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition1));
        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition2));

    }


}

