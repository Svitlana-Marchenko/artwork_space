package com.system.artworkspace.auction.job;

import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@Configuration
public class AuctionEntityItemReader {
    @Autowired
    private AuctionRepository auctionRepository;
    @Bean(name = "formingSaleReader")
    @StepScope
    public ItemReader<AuctionEntity> formingSaleReader() {
        Date currentDate = new Date();

        List<AuctionEntity> closingAuctions = auctionRepository.findClosingToday(currentDate, PageRequest.of(0, 10));

        return new ListItemReader<>(closingAuctions);
    }
}
