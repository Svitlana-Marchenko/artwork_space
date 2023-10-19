package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;

@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    public List<Auction> getAvailableAuctions() {
        Date currentDate = new Date();
        Example<AuctionEntity> example = Example.of(new AuctionEntity(), ExampleMatcher.matchingAll().withIgnorePaths("closingTime"));
        List<AuctionEntity> availableAuctionEntities = auctionRepository.findAll(example);
        logger.info(AUCTIONS_EVENTS, "Retrieved {} available auctions.", availableAuctionEntities.size());
        return (List<Auction>) availableAuctionEntities.stream().map(x -> AuctionMapper.INSTANCE.auctionEntityToAuction(x));
    }

    @Override
    public Auction placeBid(Long id, double bidAmount) {
        AuctionEntity auction = auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with ID: " + id));
        Auction auc = AuctionMapper.INSTANCE.auctionEntityToAuction(auction);
        auc.setCurrentBid(bidAmount);
        auction = AuctionMapper.INSTANCE.auctionToAuctionEntity(auc);
        auctionRepository.save(auction);
        logger.info(AUCTIONS_EVENTS, "Placed bid for auction with ID: {}. Bid amount: {}", id, bidAmount);
        return auc;
    }

    @Override
    public double getCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        logger.info(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElse(0.0);
    }

    @Override
    public Artwork getArtworkFromAuction(Long artworkId) {
        ArtworkEntity artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork != null) {
            logger.info(AUCTIONS_EVENTS, "Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), artworkId);
        } else {
            logger.warn(AUCTIONS_EVENTS, "Artwork not found for auction with ID: {}", artworkId);
        }
        return ArtworkMapper.INSTANCE.artworkEntityToArtwork(artwork);
    }
}
