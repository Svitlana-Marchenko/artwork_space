package com.system.artworkspace.auction.job;

import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AuctionEntityItemWriter {

    @Autowired
    private AuctionRepository auctionRepository;

    @Bean(name = "formingSaleWriter")
    public ItemWriter<AuctionEntity> formingSaleWriter() {
        return items -> {
            for (AuctionEntity auction : items) {
                auctionRepository.deleteById(auction.getId());
                log.info("Successfully closed auction with ID: {}", auction.getId());
            }
        };
    }
}
