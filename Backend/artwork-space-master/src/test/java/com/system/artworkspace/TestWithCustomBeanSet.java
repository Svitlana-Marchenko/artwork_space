package com.system.artworkspace;

import com.system.artworkspace.auction.*;
import com.system.artworkspace.user.UserService;
import com.system.artworkspace.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
public class TestWithCustomBeanSet {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public AuctionArtistServiceImpl auctionService() {
            return new AuctionArtistServiceImpl();
        }

        @Bean
        public AuctionCollectioneerServiceImpl auctionColService() {
            return new AuctionCollectioneerServiceImpl();
        }

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


    }

    @Autowired
    private AuctionArtistServiceImpl auctionService;

    @Autowired
    private AuctionCollectioneerServiceImpl auctionColService;


    @Test
    public void testUpdatingBidFromCollectioneerToArtist(){
        AuctionEntity a = new AuctionEntity(null, "", "", 0, 0);
        auctionService.createAuction(AuctionMapper.INSTANCE.auctionEntityToAuction(a));
        auctionColService.placeBid(1L, 100);
        assert (auctionService.displayCurrentBid(1L) == auctionColService.getCurrentBid(1L));

    }
}
