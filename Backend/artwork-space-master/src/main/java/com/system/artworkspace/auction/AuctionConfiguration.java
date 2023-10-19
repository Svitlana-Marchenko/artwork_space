package com.system.artworkspace.auction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "spring.gson.date-format",
        havingValue = "dd-MM-yyyy HH:mm:ss"
)
public class AuctionConfiguration {

    //@Bean
    //public AuctionArtistServiceImpl auctionArtistService(){
        //return new AuctionArtistServiceImpl();
   // }

}
