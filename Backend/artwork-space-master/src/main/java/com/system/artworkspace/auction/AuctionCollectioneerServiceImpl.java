package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    public List<Auction> getAvailableAuctions() {
        Date currentDate = new Date();
        Example<Auction> example = Example.of(new Auction(), ExampleMatcher.matchingAll().withIgnorePaths("closingTime"));
        List<Auction> availableAuctions = auctionRepository.findAll(example);
        logger.info("Retrieved {} available auctions.", availableAuctions.size());
        return availableAuctions;
    }

    @Override
    public Auction placeBid(Auction auction, double bidAmount) {
        auctionRepository.save(auction);
        logger.info("Placed bid for auction with ID: {}. Bid amount: {}", auction.getId(), bidAmount);
        return auction;
    }

    @Override
    public double getCurrentBid(Auction auction) {
        logger.info("Retrieved current bid for auction with ID: {}", auction.getId());
        return auction.getCurrentBid();
    }

    @Override
    public Artwork getArtworkFromAuction(Auction auction) {
        Long artworkId = auction.getId();
        Artwork artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork != null) {
            logger.info("Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), auction.getId());
        } else {
            logger.warn("Artwork not found for auction with ID: {}", auction.getId());
        }
        return artwork;
    }
}
