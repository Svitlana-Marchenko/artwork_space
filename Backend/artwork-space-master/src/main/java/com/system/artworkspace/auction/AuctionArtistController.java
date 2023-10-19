package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionArtistController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionArtistController.class);
    private final AuctionArtistService auctionService;

    @Autowired
    public AuctionArtistController(AuctionArtistService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public AuctionDto createAuction(
            @RequestBody ArtworkDto artwork,
            @RequestParam Rating rating,
            @RequestParam String auctionName,
            @RequestParam String auctionDescription,
            @RequestParam double startingPrice,
            @RequestParam double step
    ) {
        logger.info("Creating an auction for artwork with ID: {}", artwork.getId());
        AuctionDto createdAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.createAuction(ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork), rating, auctionName, auctionDescription, startingPrice, step));
        logger.info("Auction created with ID: {}", createdAuction.getId());
        return createdAuction;
    }

    @GetMapping("/{id}/currentBid")
    public double displayCurrentBid(@PathVariable Long id) {
        logger.info("Displaying current bid for auction with ID: {}", id);
        return auctionService.displayCurrentBid(id);
    }

    @GetMapping("/{id}/currentBuyer")
    public User displayCurrentBuyer(@PathVariable Long id) {
        logger.info("Displaying current buyer for auction with ID: {}", id);
        return auctionService.displayCurrentBuyer(id);

    }

    @GetMapping("/active")
    public List<AuctionDto> getAllActiveAuctions() {
        logger.info("Fetching all active auctions");
        List<AuctionDto> activeAuctions = (List<AuctionDto>) auctionService.getAllActiveAuctions().stream().map(x -> AuctionMapper.INSTANCE.auctionToAuctionDto(x));
        logger.info("Retrieved {} active auctions.", activeAuctions.size());
        return activeAuctions;
    }

    @PutMapping("/{id}/close")
    public void closeAuction(@PathVariable Long id) {
        logger.info("Closing auction with ID: {}", id);
        auctionService.closeAuction(id);
        logger.info("Auction closed with ID: {}", id);
    }

    @PutMapping("/{id}/name")
    public AuctionDto updateName(@PathVariable Long id, @RequestParam String name) {
        logger.info("Updating auction name for auction with ID: {}", id);
        AuctionDto updatedAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.updateName(id, name));
        logger.info("Auction name updated for auction with ID: {}", id);
        return updatedAuction;
    }

    @PutMapping("/{id}/description")
    public AuctionDto updateDescription(@PathVariable Long id, @RequestParam String newDescription) {
        logger.info("Updating auction description for auction with ID: {}", id);
        AuctionDto updatedAuction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.updateDescription(id, newDescription));
        logger.info("Auction description updated for auction with ID: {}", id);
        return updatedAuction;
    }
}
