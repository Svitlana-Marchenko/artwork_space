package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectioneer/auctions")
public class AuctionCollectioneerController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionCollectioneerController.class);

    private final AuctionCollectioneerService auctionCollectioneerService;

    @Autowired
    public AuctionCollectioneerController(AuctionCollectioneerService auctionCollectioneerService) {
        this.auctionCollectioneerService = auctionCollectioneerService;
    }

    @GetMapping("/available")
    public List<AuctionDto> getAvailableAuctions() {
        logger.info("Fetching available auctions for collectioneer");
        List<AuctionDto> availableAuctions = auctionCollectioneerService.getAvailableAuctions();
        logger.info("Retrieved {} available auctions for collectioneer.", availableAuctions.size());
        return availableAuctions;
    }

    @PutMapping("/{id}/placeBid")
    public AuctionDto placeBid(@PathVariable Long id, @RequestParam double bidAmount) {
        logger.info("Placing a bid for auction with ID: {}. Bid amount: {}", id, bidAmount);
//        AuctionDto auction = auctionCollectioneerService.placeBid(id, bidAmount);
//        logger.info("Bid placed for auction with ID: {}. Current bid: {}", auction.getId(), auction.getCurrentBid());
//        return auction;
        return null;
    }

    @GetMapping("/{id}/currentBid")
    public double getCurrentBid(@PathVariable Long id) {
        logger.info("Retrieving current bid for auction with ID: {}", id);
        //return auctionCollectioneerService.getCurrentBid(id);
        return 0;
    }

    @GetMapping("/{id}/artwork")
    public ArtworkDto getArtworkFromAuction(@PathVariable Long id) {
        logger.info("Fetching artwork from auction with ID: {}", id);
//        ArtworkDto artwork = auctionCollectioneerService.getArtworkFromAuction(id);
//        if (artwork != null) {
//            logger.info("Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), id);
//        } else {
//            logger.warn("Artwork not found for auction with ID: {}", id);
//        }
//        return artwork;
        return null;
    }
}
