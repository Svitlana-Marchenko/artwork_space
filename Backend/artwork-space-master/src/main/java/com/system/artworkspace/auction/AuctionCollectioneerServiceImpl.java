package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.exceptions.NoSuchAuctionException;
import com.system.artworkspace.user.Role;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;

@Service
@Slf4j
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    
    @Autowired
    private AuctionRepository auctionRepository;
    
    @Autowired
    private ArtworkRepository artworkRepository;
    
    @Autowired
    private UserService userService;

    public List<Auction> getAvailableAuctions() {

        Sort sort = Sort.by(Sort.Order.asc("closingTime"));

        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAll(sort);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate = dateFormat.format(new Date());

        List<AuctionEntity> validAuctionEntities = activeAuctionEntities.stream()
                .filter(entity -> {
                    try {
                        Date closingTime = dateFormat.parse(entity.getClosingTime().toString());
                        return !closingTime.before(dateFormat.parse(formattedCurrentDate));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        log.info(AUCTIONS_EVENTS, "Retrieved {} active auctions.", validAuctionEntities.size());

        return validAuctionEntities.stream()
                .map(AuctionMapper.INSTANCE::auctionEntityToAuction)
                .collect(Collectors.toList());
    }

    @Override
    public Auction placeBid(Long id, double bidAmount) {
        AuctionEntity auction = auctionRepository.findById(id)
                .orElseThrow(() -> new NoSuchAuctionException("Auction not found with ID: " + id));
        Auction auc = AuctionMapper.INSTANCE.auctionEntityToAuction(auction);
        if (bidAmount < auc.getCurrentBid()+auc.getBid())
            throw new RuntimeException("Trying to place incorrect bid");
        auc.setCurrentBid(bidAmount);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = userService.getUserByUsername(userId);
        auc.setUser(user);
        auction = AuctionMapper.INSTANCE.auctionToAuctionEntity(auc);
        auctionRepository.save(auction);
        log.info(AUCTIONS_EVENTS, "Placed bid for auction with ID: {}. Bid amount: {}", id, bidAmount);
        return auc;
    }

    @Override
    public double getCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        log.debug(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElseThrow(() -> new NoSuchAuctionException ("Auction with id "+id+" not found"));
    }

    @Override
    public Artwork getArtworkFromAuction(Long artworkId) {
        ArtworkEntity artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork != null) {
            log.info(AUCTIONS_EVENTS, "Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), artworkId);
        } else {
            log.warn(AUCTIONS_EVENTS, "Artwork not found for auction with ID: {}", artworkId);
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
            log.debug(AUCTIONS_EVENTS, "Retrieved auction for painting with ID: {}", paintingId);
            return auction;
        } else {
            log.warn(AUCTIONS_EVENTS, "Artwork not found for painting with ID: {}", paintingId);
            return null;
        }
    }

    @Override
    public Auction getAuctionById(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        log.debug(AUCTIONS_EVENTS, "Getting auction with ID: {}", id);

        if (auction.isPresent())
            return AuctionMapper.INSTANCE.auctionEntityToAuction(auction.get());
        else
            throw new NoSuchAuctionException("Auction with id " + id + " not found");
    }

    @Override
    public List<Auction> getAllAuctionsByCustomerId(Long id) {
        log.debug("Getting auctions, where artist id {}", id);
        User user = userService.getUserById(id);
        if(!user.getRole().equals(Role.COLLECTIONEER)){
            throw new RuntimeException("Trying to get auctions with non collectioneer user");
        }
        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAllAuctionsByUserId(id);
        return activeAuctionEntities.stream()
                .map(AuctionMapper.INSTANCE::auctionEntityToAuction)
                .collect(Collectors.toList());
    }
}
