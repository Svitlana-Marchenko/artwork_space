package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_EVENTS;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Autowired
    private AuctionRepository auctionRepository;

   /* @Override
    public Auction createAuction(Artwork artwork, RatingEntity rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        AuctionEntity auctionEntity = new AuctionEntity(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork), rating, auctionName, auctionDescription, startingPrice, step);
        auctionRepository.save(auctionEntity);
        logger.info(AUCTIONS_EVENTS, "Created auction with ID: {}", auctionEntity.getId());
        return AuctionMapper.INSTANCE.auctionEntityToAuction(auctionEntity);
    }*/

    @Override
    public Auction createAuction(Auction auction) {
        auction.setCurrentBid(auction.getStartingPrice());
        auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
        logger.info(AUCTIONS_EVENTS, "Created auction with ID: {}", auction.getId());
        return auction;
    }

    @Override
    public double displayCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        logger.info(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElse(0.0);
    }

    @Override
    public User displayCurrentBuyer(Long id) {
        logger.info(CONFIDENTIAL_EVENTS, "Displaying current buyer for auction with ID: {}", id);
        if (auctionRepository.findById(id).isPresent())
            return UserMapper.INSTANCE.userEntityToUser(auctionRepository.findById(id).get().getUser());
        return null;
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        Date currentDate = new Date();
        Specification<AuctionEntity> closingTimeSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("closingTime"), currentDate);

        Sort sort = Sort.by(Sort.Order.asc("closingTime"));
        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAll(sort);

        List<AuctionEntity> validAuctionEntities = activeAuctionEntities;

//        List<AuctionEntity> validAuctionEntities = activeAuctionEntities.stream()
//                .filter(entity -> entity.getClosingTime().after(currentDate))
//                .collect(Collectors.toList());

        logger.info(AUCTIONS_EVENTS, "Retrieved {} active auctions.", validAuctionEntities.size());

        return validAuctionEntities.stream()
                .map(x -> AuctionMapper.INSTANCE.auctionEntityToAuction(x))
                .collect(Collectors.toList());
    }

    //TODO think about logic of method
    @Override
    public void closeAuction(Long id) {
        // Update the auction as closed
        //auction.setClosed(true);
        //auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
        logger.info(AUCTIONS_EVENTS, "Closed auction with ID: {}", id);
    }


}
