package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_EVENTS;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Auction createAuction(Artwork artwork, RatingEntity rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        AuctionEntity auctionEntity = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork), rating, auctionName, auctionDescription, startingPrice, step);
        auctionRepository.save(auctionEntity);
        logger.info(AUCTIONS_EVENTS,"Created auction with ID: {}", auctionEntity.getId());
        return AuctionMapper.INSTANCE.auctionEntityToAuction(auctionEntity);
    }

    @Override
    public double displayCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        logger.info(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElse(0.0);
    }

    //TODO after user mapper
    @Override
    public UserEntity displayCurrentBuyer(Long id) {
        logger.info(CONFIDENTIAL_EVENTS,"Displaying current buyer for auction with ID: {}", id);
        //return (UserEntity) auction.convertToAuction().getCurrentBuyer();
        return null;
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        Date currentDate = new Date();
        Specification<AuctionEntity> closingTimeSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("closingTime"), currentDate);

        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAll((Sort) closingTimeSpecification);
        logger.info(AUCTIONS_EVENTS,"Retrieved {} active auctions.", activeAuctionEntities.size());
        return (List<Auction>) activeAuctionEntities.stream().map(x -> AuctionMapper.INSTANCE.auctionEntityToAuction(x));
    }

    //TODO think about logic of method
    @Override
    public void closeAuction(Long id) {
        // Update the auction as closed
        //auction.setClosed(true);
        //auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
        logger.info(AUCTIONS_EVENTS,"Closed auction with ID: {}", id);
    }

    @Override
    public Auction updateName(Long id, String name) {
        Optional<AuctionEntity> auctionE = auctionRepository.findById(id);
        if(auctionE.isPresent()) {
            Auction auction = AuctionMapper.INSTANCE.auctionEntityToAuction(auctionE.get());
            auction.setAuctionName(name);
            auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
            logger.info(AUCTIONS_EVENTS, "Updated auction name for auction with ID: {}", auction.getId());
            return auction;
        }
        return null;
    }

    @Override
    public Auction updateDescription(Long id, String newDescription) {
        Optional<AuctionEntity> auctionE = auctionRepository.findById(id);
        if(auctionE.isPresent()) {
            Auction auction = AuctionMapper.INSTANCE.auctionEntityToAuction(auctionE.get());
            auction.setAuctionDescription(newDescription);
            auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
            logger.info(AUCTIONS_EVENTS, "Updated auction name for auction with ID: {}", auction.getId());
            return auction;
        }
      return null;
    }
}
