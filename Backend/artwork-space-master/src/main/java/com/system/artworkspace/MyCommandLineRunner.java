package com.system.artworkspace;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkService;
import com.system.artworkspace.auction.*;
import com.system.artworkspace.auction.Sale.SaleEntity;
import com.system.artworkspace.auction.Sale.SaleMapper;
import com.system.artworkspace.auction.Sale.SaleService;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Profile("dev")
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    ExhibitionService exhibitionService;

    @Autowired
    ArtworkService artworkService;

    @Autowired
    SaleService saleService;

    @Autowired
    AuctionArtistService auctionArtistService;

    @Override
    public void run(String... args) {
        createTestData();
    }

    private void createTestData() {
        List<UserEntity> users = new ArrayList<>();
        UserEntity u1 = new UserEntity("art", "John", "Doe", "john.doe@example.com", "password", Role.ARTIST);
        UserEntity u2 = new UserEntity("col", "Anton", "Doe", "anton.doe@example.com", "password", Role.COLLECTIONEER);
        UserEntity u3 = new UserEntity("cur", "Alex", "Doe", "alex.doe@example.com", "password", Role.CURATOR);
        UserEntity u4  = (new UserEntity("alice_art", "Alice", "Smith", "alice.smith@gmail.com", "password", Role.ARTIST));
        UserEntity u5 = (new UserEntity("robert_johnson", "Robert", "Johnson", "robert.johnson@gmail.com", "password", Role.COLLECTIONEER));
        UserEntity u6 = (new UserEntity("emily_exh", "Emily", "Anderson", "emily.anderson@gmail.com", "password", Role.CURATOR));

        UserEntity c1 = (new UserEntity("anna_doys", "Anna", "Doyson", "anna.doyson@gmail.com", "password", Role.CURATOR));
        UserEntity c2 = (new UserEntity("david_stark", "David", "Stark", "david.stark@gmail.com", "password", Role.CURATOR));
        UserEntity c3 = (new UserEntity("andrew_spens", "Andrew", "Spensor", "andrew.spensor@gmail.com", "password", Role.CURATOR));
        UserEntity c4 = (new UserEntity("elizabeth_bret", "Elizabeth", "Bret", "elizabeth.bret@gmail.com", "password", Role.CURATOR));


        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);


        users.add(new UserEntity("williams_artist", "David", "Williams", "david.williams@gmail.com", "password", Role.ARTIST));
        users.add(new UserEntity("sara_art_lover", "Sarah", "Brown", "sarah.brown@example.com", "password", Role.COLLECTIONEER));
        users.add(new UserEntity("art_michael", "Michael", "Davis", "michael.davis@example.com", "password", Role.CURATOR));
        users.add(new UserEntity("olivko", "Olivia", "Wilson", "olivia.wilson@example.com", "password", Role.ARTIST));
        users.add(new UserEntity("ethan_martin", "Ethan", "Martin", "ethan.martin@example.com", "password", Role.COLLECTIONEER));
        users.add(new UserEntity("sofa_sofa", "Sophia", "Thompson", "sophia.thompson@example.com", "password", Role.CURATOR));
        users.add(new UserEntity("noah_clark", "Noah", "Clark", "noah.clark@example.com", "password", Role.ARTIST));
        users.add(new UserEntity("ava_ava", "Ava", "Moore", "ava.moore@example.com", "password", Role.COLLECTIONEER));
        users.add(new UserEntity("liam_artlover", "Liam", "White", "liam.white@example.com", "password", Role.CURATOR));
        users.add(c1);
        users.add(c2);
        users.add(c3);
        users.add(c4);
        for (UserEntity a: users){
            userService.createUser(UserMapper.INSTANCE.userEntityToUser(a));
        }

        List<ArtworkEntity> artworks = new ArrayList<>();

        RatingEntity r1 = new RatingEntity(9, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)), "Very beautiful artwork! I am impressed");
        RatingEntity r2 = new RatingEntity(0, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(6L)), "It is fake !!!!!!!! I have seen the same in my local gallery! Why do you post a replica?");
        RatingEntity r3 = new RatingEntity(6, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(16L)), "Not bad.");
        RatingEntity r4 = new RatingEntity(2, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(17L)), "");
        RatingEntity r5 = new RatingEntity(10, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(18L)), "Oh my Goodness! I can`t believe my eyes! It is an absolute masterpiece! I am totally in love! The artist is soooooo talented! Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :* Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :* Pleaseeeee put it on the auction, I will buy it for 100000000000000$ :*");
        RatingEntity r6 = new RatingEntity(8, UserMapper.INSTANCE.userToUserEntity(userService.getUserById(19L)), "I like this artwork! Especially a combination of colours!");

        List<RatingEntity> ratings = new ArrayList<>();
        ratings.add(r3);
        ratings.add(r4);
        ratings.add(r1);
        ratings.add(r2);
        ratings.add(r5);
        ratings.add(r6);


        ArtworkEntity art1 = new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Янголи", "Неймовірної краси янголи", "Oil Painting", 60.0, 40.0, "../data/artist_1/1.jpg", ratings);//../../../data/artist_1/1.jpg
        artworks.add(art1);
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_1/2.jpg", ratings));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Ліс восени", "Чарівний ліс восени, коли листя стає яскравими фарбами.", "Oil Painting", 60.0, 40.0, "../data/artist_1/3.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Битва за свободу", "Мистецьке відтворення історичної битви за свободу.", "Oil Painting", 60.0, 40.0, "../data/artist_1/4.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(1L)), "Яскраві квіти", "Сповнені кольору квіти, які вражають своєю красою.", "Oil Painting", 60.0, 40.0, "../data/artist_1/5.jpg", new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Абстрактний вибух кольору", "Ексклюзивна абстрактна робота", "Oil Painting", 60.0, 40.0, "../data/artist_4/6.jpg", ratings));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Абстрактний витвір", "Сучасний абстрактний твір", "Oil Painting", 60.0, 40.0, "../data/artist_4/7.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Абстрактні думки", "Мої справжні думки", "Oil Painting", 60.0, 40.0, "../data/artist_4/8.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Абстрактний вибух зебр", "Експлозивна абстрактна робота з тваринними мотивами", "Oil Painting", 60.0, 40.0, "../data/artist_4/9.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Портрет таємничого ведмедя", "Портрет ведмедика з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_4/10.jpeg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)), "Абстракція", "Сучасний абстракція, яка залишає всіх враженими", "Oil Painting", 60.0, 40.0, "../data/artist_4/11.jpg", new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Погляд", "Погляд, який залишає всіх враженими.", "Oil Painting", 80.0, 50.0, "../data/artist_7/12.jpeg", ratings));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Ранок у колі родини", "Родинний ранок мами з ведмежатами", "Oil Painting", 60.0, 40.0, "../data/artist_7/13.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Серйозність", "Портрет носоріга з загадковими очима та серйозним виразом обличчя.", "Oil Painting", 80.0, 30.0, "../data/artist_7/14.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Погляд прискіпливості", "Портрет лами з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_7/15.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_7/16.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Ранок у горах", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_7/17.jpg", ratings));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Ранок в оточенні свійських тварин", "Живописний ранок з різноманітними тваринами", "Oil Painting", 60.0, 40.0, "../data/artist_7/18.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Акварельні квіти", "Живописний квіти", "Watercolor Painting", 60.0, 40.0, "../data/artist_7/19.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(7L)), "Ведмідь сидить", "Ведмідь з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 70.0, "../data/artist_7/20.jpg", new ArrayList<>()));

        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Квіти", "Живописні квіти", "Oil Painting", 60.0, 40.0, "../data/artist_10/21.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Ранок у горах з озером", "Живописний ранок у горах", "Oil Painting", 60.0, 40.0, "../data/artist_10/22.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Питання?", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_10/23.jpeg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Квіти на зеленому фоні", "Ексклюзивна робота", "Oil Painting", 60.0, 40.0, "../data/artist_10/24.jpg", new ArrayList<>()));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Насолода", "Сучасний твір", "Oil Painting", 60.0, 40.0, "../data/artist_10/25.jpg", ratings));
        artworks.add(new ArtworkEntity(UserMapper.INSTANCE.userToUserEntity(userService.getUserById(10L)), "Портрет таємничої корови", "Портрет корови з загадковими очима та виразом обличчя, який залишає всіх враженими.", "Oil Painting", 80.0, 30.0, "../data/artist_10/26.jpeg", new ArrayList<>()));

        for (ArtworkEntity a : artworks) {
            artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkEntityToArtwork(a));
        }

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

        AuctionEntity aa1 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(1L)), 10.0, 5, new Date(), null, 0);
        AuctionEntity aa2 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(2L)), 30.0, 10, endDate2, null, 0);
        AuctionEntity aa3 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(3L)), 100.0, 15, new Date(), u2, 150);
        AuctionEntity aa4 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(4L)), 50.0, 5, new Date(), u2, 70);
        AuctionEntity aa5 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(20L)), 70.0, 1, new Date(), u2, 80);
        AuctionEntity aa6 = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(21L)), 90.0, 20, new Date(), null, 0);

        u2.setId(2L);

        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa1));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa2));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa3));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa4));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa5));
        auctionArtistService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(aa6));


        CollectionEntity collection1 = new CollectionEntity("Test",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
        CollectionEntity collection2 = new CollectionEntity("COLLECTION WOW",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));
        CollectionEntity collection3 = new CollectionEntity("HAHA FUNNY",UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)));

        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collection1));
        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collection2));
        collectionService.createCollection(CollectionMapper.INSTANCE.collectionEntityToCollection(collection3));

        collectionService.addToCollection(1L,6L);
        collectionService.addToCollection(1L,7L);
        collectionService.addToCollection(1L,8L);
        collectionService.addToCollection(1L,9L);
        collectionService.addToCollection(1L,10L);

        collectionService.addToCollection(2L,1L);
        collectionService.addToCollection(2L,2L);
        collectionService.addToCollection(2L,3L);
        collectionService.addToCollection(2L,7L);
        collectionService.addToCollection(2L,6L);

        ExhibitionEntity exhibition1 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),
                "Абстракція",
                "Виставка показує основні твори абстрактного мистецтва, які представлені на мистецькому порталі",
                new LinkedList<ArtworkEntity>(),
                startDate1,
                endDate1
        );

        ExhibitionEntity exhibition2 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(3L)),
                "Світ ведмедя",
                "Найкращі картини ведмедів з мистецього порталу",
                new LinkedList<ArtworkEntity>(),
                startDate2,
                endDate2
        );

        ExhibitionEntity exhibition3 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(6L)),
                "Світ тварин",
                "Найкращі картини тварин з мистецього порталу",
                new LinkedList<ArtworkEntity>(),
                startDate1,
                endDate2
        );

        ExhibitionEntity exhibition4 = new ExhibitionEntity(
                UserMapper.INSTANCE.userToUserEntity(userService.getUserById(6L)),
                "Вибрані твори",
                "Найкращі твори з мистецього порталу",
                new LinkedList<ArtworkEntity>(),
                startDate2,
                endDate2
        );

        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition1));
        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition2));
        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition3));
        exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibition4));

        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(6L));
        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(7L));
        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(8L));
        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(9L));
        exhibitionService.addToExhibition(1L, artworkService.findArtworkById(11L));

        exhibitionService.addToExhibition(2L, artworkService.findArtworkById(10L));
        exhibitionService.addToExhibition(2L, artworkService.findArtworkById(12L));
        exhibitionService.addToExhibition(2L, artworkService.findArtworkById(13L));
        exhibitionService.addToExhibition(2L, artworkService.findArtworkById(20L));
        exhibitionService.addToExhibition(2L, artworkService.findArtworkById(25L));

        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(14L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(23L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(13L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(15L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(12L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(26L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(10L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(16L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(20L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(18L));
        exhibitionService.addToExhibition(3L, artworkService.findArtworkById(25L));

        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(19L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(8L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(4L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(5L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(3L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(24L));
        exhibitionService.addToExhibition(4L, artworkService.findArtworkById(11L));

        try {
            SaleEntity sale = new SaleEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(7L)),UserMapper.INSTANCE.userToUserEntity(userService.getUserById(2L)), UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)),200.0,dateFormat.parse("2023-10-23"));
            saleService.createSale(SaleMapper.INSTANCE.saleEntityToSale(sale));
            SaleEntity sale2 = new SaleEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artworkService.findArtworkById(8L)),UserMapper.INSTANCE.userToUserEntity(userService.getUserById(5L)), UserMapper.INSTANCE.userToUserEntity(userService.getUserById(4L)),8990.0,dateFormat.parse("2023-10-10"));
            saleService.createSale(SaleMapper.INSTANCE.saleEntityToSale(sale2));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

