package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;

@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    public List<Auction> getAvailableAuctions() {
        Date currentDate = new Date();
        Specification<AuctionEntity> closingTimeSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("closingTime"), currentDate);

        Sort sort = Sort.by(Sort.Order.asc("closingTime"));
        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAll(sort);

        List<AuctionEntity> validAuctionEntities = activeAuctionEntities.stream()
                .filter(entity -> entity.getClosingTime().after(currentDate))
                .collect(Collectors.toList());

        logger.info(AUCTIONS_EVENTS, "Retrieved {} active auctions.", validAuctionEntities.size());

        return validAuctionEntities.stream()
                .map(x -> AuctionMapper.INSTANCE.auctionEntityToAuction(x))
                .collect(Collectors.toList());
    }

    @Override
    public Auction placeBid(Long id, double bidAmount) {
        AuctionEntity auction = auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with ID: " + id));
        Auction auc = AuctionMapper.INSTANCE.auctionEntityToAuction(auction);
        auc.setCurrentBid(bidAmount);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = userService.getUserByUsername(userId);
        auc.setUser(user);
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

    @Override
    public Auction getAuctionByPaintingId(Long paintingId) {
        ArtworkEntity artwork = artworkRepository.findById(paintingId).orElse(null);

        if (artwork != null) {
            Long auctionId = artwork.getId();

            AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new EntityNotFoundException("Auction not found for painting with ID: " + paintingId));

            Auction auction = AuctionMapper.INSTANCE.auctionEntityToAuction(auctionEntity);

            logger.info(AUCTIONS_EVENTS, "Retrieved auction for painting with ID: {}", paintingId);

            return auction;
        } else {
            logger.warn(AUCTIONS_EVENTS, "Artwork not found for painting with ID: {}", paintingId);
            return null;
        }
    }

}
