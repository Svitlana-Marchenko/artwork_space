package com.system.artworkspace.auction.job;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionMapper;
import com.system.artworkspace.auction.AuctionRepository;
import com.system.artworkspace.auction.Sale.Sale;
import com.system.artworkspace.auction.Sale.SaleService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuctionEntityItemProcessor {

    @Autowired
    private SaleService saleService;

    @Bean(name = "formingSaleProcessor")
    public ItemProcessor<AuctionEntity, AuctionEntity> formingSaleProcessor() {
        return auctionEntity -> {
            Auction auction = AuctionMapper.INSTANCE.auctionEntityToAuction(auctionEntity);
            saleService.createSale(new Sale(auction.getArtwork(),auction.getUser(),auction.getArtwork().getUser(),auction.getCurrentBid(),auction.getClosingTime()));
            return auctionEntity;
        };
    }

}
