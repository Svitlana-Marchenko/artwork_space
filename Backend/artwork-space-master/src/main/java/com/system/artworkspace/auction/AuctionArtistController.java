package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
@PreAuthorize("hasAuthority('ARTIST')")
public class AuctionArtistController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionArtistController.class);
    private final AuctionArtistService auctionService;

    @Autowired
    public AuctionArtistController(AuctionArtistService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public AuctionDto createAuction(
            @RequestBody AuctionDto auction
    ) {
        logger.info("Creating an auction for artwork with ID: {}", auction.getArtwork().getId());
        AuctionDto createdAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.createAuction(AuctionMapper.INSTANCE.auctionDtoToAuction(auction)));
        logger.info("Auction created with ID: {}", createdAuction.getId());
        return createdAuction;
    }

    @GetMapping("/{id}/currentBid")
    public double displayCurrentBid(@PathVariable Long id) {
        logger.info("Displaying current bid for auction with ID: {}", id);
        return auctionService.displayCurrentBid(id);
    }

    @GetMapping("/{id}/currentBuyer")
    public UserDto displayCurrentBuyer(@PathVariable Long id) {
        logger.info("Displaying current buyer for auction with ID: {}", id);
        User current = auctionService.displayCurrentBuyer(id);
        return UserMapper.INSTANCE.userToUserDto(current);

    }

    @GetMapping("/active")
    public List<AuctionDto> getAllActiveAuctions() {
        logger.info("Fetching all active auctions");
        List<AuctionDto> activeAuctions = auctionService.getAllActiveAuctions().stream().map(x -> AuctionMapper.INSTANCE.auctionToAuctionDto(x)).collect(Collectors.toList());
        logger.info("Retrieved {} active auctions.", activeAuctions.size());
        return activeAuctions;
    }

    @PutMapping("/{id}/close")
    public void closeAuction(@PathVariable Long id) {
        logger.info("Closing auction with ID: {}", id);
        auctionService.closeAuction(id);
        logger.info("Auction closed with ID: {}", id);
    }

    @PutMapping("/{id}/title")
    public AuctionDto updateName(@PathVariable Long id, @RequestParam String title) {
        logger.info("Updating auction name for auction with ID: {}", id);
        AuctionDto updatedAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.updateName(id, title));
        logger.info("Auction name updated for auction with ID: {}", id);
        return updatedAuction;
    }

    @PutMapping("/{id}/description")
    public AuctionDto updateDescription(@PathVariable Long id, @RequestParam String description) {
        logger.info("Updating auction description for auction with ID: {}", id);
        AuctionDto updatedAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.updateDescription(id, description));
        logger.info("Auction description updated for auction with ID: {}", id);
        return updatedAuction;
    }
}
