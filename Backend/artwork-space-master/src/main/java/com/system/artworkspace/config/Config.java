package com.system.artworkspace.config;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.user.Artist;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.Curator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public Artist artist(){
        return new Artist(

                "blapblap",
                "Ihor",
                "Semenko",
                "semenko@gmail.com",
                "11111"
        );
    }
    @Bean
    public Auction auction(){
        return new Auction();
    }
    @Bean
    public Curator curator() {
        return new Curator(
                "11111",
                "blapblap",
                "Ihor",
                "Semenko",
                "semenko@gmail.com",
                "11111"
        );
    }

    @Bean
    public Collectioneer collectioneer() {
        return new Collectioneer(
                "11111",
                "blapblap",
                "Ihor",
                "Semenko",
                "semenko@gmail.com",
                "11111"
        );
    }
    @Bean
    public List<Artwork> artworks() {
        return new ArrayList<>();
    }

}
