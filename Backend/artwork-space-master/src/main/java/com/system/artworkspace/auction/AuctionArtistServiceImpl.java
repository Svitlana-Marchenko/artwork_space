package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_EVENTS;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Autowired
    private AuctionRepository auctionRepository;
    @Override
    public AuctionDto createAuction(ArtworkDto artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        Auction auction = new Auction(artwork.convertToArtwork(), rating, auctionName, auctionDescription, startingPrice, step);
        auctionRepository.save(auction);
        logger.info(AUCTIONS_EVENTS,"Created auction with ID: {}", auction.getId());
        return auction.convertToAuctionDto();
    }

    @Override
    public double displayCurrentBid(AuctionDto auction) {
        logger.info(AUCTIONS_EVENTS,"Displaying current bid for auction with ID: {}", auction.getId());
        return auction.getCurrentBid();
    }

    @Override
    public User displayCurrentBuyer(AuctionDto auction) {
        logger.info(CONFIDENTIAL_EVENTS,"Displaying current buyer for auction with ID: {}", auction.getId());
        return (User) auction.convertToAuction().getCurrentBuyer();
    }

    @Override
    public List<AuctionDto> getAllActiveAuctions() {
        Date currentDate = new Date();
        Specification<Auction> closingTimeSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("closingTime"), currentDate);

        List<Auction> activeAuctions = auctionRepository.findAll((Sort) closingTimeSpecification);
        logger.info(AUCTIONS_EVENTS,"Retrieved {} active auctions.", activeAuctions.size());
        return (List<AuctionDto>)activeAuctions.stream().map(x -> x.convertToAuctionDto());
    }

    @Override
    public void closeAuction(AuctionDto auction) {
        // Update the auction as closed
        //auction.setClosed(true);
        auctionRepository.save(auction.convertToAuction());
        logger.info(AUCTIONS_EVENTS,"Closed auction with ID: {}", auction.getId());
    }

    @Override
    public AuctionDto updateName(AuctionDto auction, String name) {
        auction.setAuctionName(name);
        auctionRepository.save(auction.convertToAuction());
        logger.info(AUCTIONS_EVENTS,"Updated auction name for auction with ID: {}", auction.getId());
        return auction;
    }

    @Override
    public AuctionDto updateDescription(AuctionDto auction, String newDescription) {
        auction.setAuctionDescription(newDescription);
        auctionRepository.save(auction.convertToAuction());
        logger.info(AUCTIONS_EVENTS,"Updated auction description for auction with ID: {}", auction.getId());
        return auction;
    }
}
