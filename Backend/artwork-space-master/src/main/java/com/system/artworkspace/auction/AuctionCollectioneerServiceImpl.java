package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;

@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    public List<AuctionDto> getAvailableAuctions() {
        Date currentDate = new Date();
        Example<Auction> example = Example.of(new Auction(), ExampleMatcher.matchingAll().withIgnorePaths("closingTime"));
        List<Auction> availableAuctions = auctionRepository.findAll(example);
        logger.info(AUCTIONS_EVENTS,"Retrieved {} available auctions.", availableAuctions.size());
        return (List<AuctionDto>)availableAuctions.stream().map(x -> x.convertToAuctionDto());
    }

    @Override
    public AuctionDto placeBid(AuctionDto auction, double bidAmount) {
        auctionRepository.save(auction.convertToAuction());
        logger.info(AUCTIONS_EVENTS,"Placed bid for auction with ID: {}. Bid amount: {}", auction.getId(), bidAmount);
        return auction;
    }

    @Override
    public double getCurrentBid(AuctionDto auction) {
        logger.info(AUCTIONS_EVENTS,"Retrieved current bid for auction with ID: {}", auction.getId());
        return auction.getCurrentBid();
    }

    @Override
    public ArtworkDto getArtworkFromAuction(AuctionDto auction) {
        Long artworkId = auction.getId();
        Artwork artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork != null) {
            logger.info(AUCTIONS_EVENTS,"Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), auction.getId());
        } else {
            logger.warn(AUCTIONS_EVENTS,"Artwork not found for auction with ID: {}", auction.getId());
        }
        return artwork.convertToArtworkDto();
    }
}
