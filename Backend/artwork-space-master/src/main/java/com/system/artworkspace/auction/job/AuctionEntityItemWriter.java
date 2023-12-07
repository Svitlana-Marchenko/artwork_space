package com.system.artworkspace.auction.job;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuctionEntityItemWriter {

    @Autowired
    private AuctionRepository auctionRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Bean(name = "formingSaleWriter")
    public ItemWriter<AuctionEntity> formingSaleWriter() {
        return items -> {
            for (AuctionEntity auction : items) {
                auctionRepository.deleteById(auction.getId());
                logger.info("Successfully closed auction with ID: {}", auction.getId());
            }
        };
    }

    @Bean(name = "closingAuctionsWriter")
    public ItemWriter<AuctionEntity> closingAuctionsWriter() {
        return items -> {
            for (AuctionEntity auction : items) {
                logger.info("Deleted auction with ID: {}", auction.getId());
            }
        };
    }
}
