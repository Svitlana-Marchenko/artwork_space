package com.system.artworkspace.config;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.artwork.Artwork;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public Auction auction(){
        return new Auction();
    }

    @Bean
    public List<Artwork> artworks() {
        return new ArrayList<>();
    }

}
