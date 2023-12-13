package com.system.artworkspace;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkService;
import com.system.artworkspace.auction.*;
import com.system.artworkspace.collection.CollectionEntity;
import com.system.artworkspace.collection.CollectionMapper;
import com.system.artworkspace.collection.CollectionService;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import com.system.artworkspace.exhibition.ExhibitionService;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
    public void run(String... args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        createTestData();
    }


    @SuppressWarnings("deprecation")
    private void createTestData() {
        UserEntity u1 = new UserEntity("art", "John", "Doe", "john.doe@example.com", "password", Role.ARTIST, new ArrayList<ArtworkEntity>());
        UserEntity u2 = new UserEntity("col", "Anton", "Doe", "anton.doe@example.com", "password", Role.COLLECTIONEER, new ArrayList<ArtworkEntity>());
        UserEntity u3 = new UserEntity("cur", "Alex", "Doe", "alex.doe@example.com", "password", Role.CURATOR, new ArrayList<ArtworkEntity>());

        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u1));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u2));
        userService.createUser(UserMapper.INSTANCE.userEntityToUser(u3));

        List<ArtworkEntity> artworks = new ArrayList<>();

        RatingEntity r1 = new RatingEntity(9,UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),"Very beautiful artwork! I am impressed");
        RatingEntity r2 = new RatingEntity(2,UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),"It is fake !!!!!!!! Fuck you people! I hate that! And what can you do? Nothing ahahahhahahah");
        RatingEntity r3 = new RatingEntity(6,UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),"Not bad.");
        RatingEntity r4 = new RatingEntity(0,UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),"");
        RatingEntity r5 = new RatingEntity(10,UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),"Oh my Goodness! I can`t believe my eyes! It is an absolute masterpiece! I am totally in love! The artist is soooooo talented! Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :* Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :* Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :*");

        List<RatingEntity> ratings = new ArrayList<>();
        ratings.add(r3);
        ratings.add(r4);
        ratings.add(r1);
        ratings.add(r2);
        ratings.add(r5);

        ArtworkEntity art1 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/5.jpg", ratings);//../../../data/artist_1/1.jpg
        artworks.add(art1);
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0,30.0, "../data/artist_1/1.jpg",  ratings.subList(0,2)));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../data/artist_1/2.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../data/artist_1/3.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/4.jpg", new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/5.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_1/1.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../data/artist_1/2.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../data/artist_1/3.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/4.jpg",  new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/5.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0,"../data/artist_1/1.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../data/artist_1/2.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Абстрактний вибух кольору", "Експлозивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../data/artist_1/3.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/4.jpg",  new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/5.jpg",  new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0,"../data/artist_1/1.jpg", new ArrayList<>()));


        for (ArtworkEntity a: artworks){
            artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a));
        }

        AuctionEntity aa1 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(1L)),  0.0, 10, new Date(), null, 0);
        AuctionEntity aa2 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(2L)),  0.0, 10, new Date(), null, 0);
        AuctionEntity aa3 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(3L)),  100.0, 10, new Date(), u2, 100);
        u2.setId(2L);

        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa1));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa2));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa3));

//        CollectionEntity collectionEntity1 = new CollectionEntity("Coll1",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
//        CollectionEntity collectionEntity2 = new CollectionEntity("Coll2",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
//        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collectionEntity1));
//        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collectionEntity2));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate1 = null;
        Date endDate1 = null;
        Date startDate2 = null;
        Date endDate2 = null;

        try {
            startDate1 = dateFormat.parse("2023-01-23");
            endDate1 = dateFormat.parse("2024-02-23");
            startDate2 = dateFormat.parse("2023-10-23");
            endDate2 = dateFormat.parse("2023-12-23");
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

        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(1l));
    }


}

