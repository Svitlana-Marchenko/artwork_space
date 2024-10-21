package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/collectioneer/auctions")
@Slf4j
public class AuctionCollectioneerController {

    private final AuctionCollectioneerService auctionCollectioneerService;

    @Autowired
    public AuctionCollectioneerController(AuctionCollectioneerService auctionCollectioneerService) {
        this.auctionCollectioneerService = auctionCollectioneerService;
    }

    @GetMapping("/available")
    public List<AuctionDto> getAvailableAuctions() {
        log.debug("Fetching available auctions for collectioneer");
        List<AuctionDto> availableAuctions = auctionCollectioneerService.getAvailableAuctions().stream().map(AuctionMapper.INSTANCE::auctionToAuctionDto).collect(Collectors.toList());
        log.info("Retrieved {} available auctions for collectioneer.", availableAuctions.size());
        return availableAuctions;
    }

    @GetMapping("/user/{id}")
    public List<AuctionDto> getAllAuctionsByCustomerId(@PathVariable Long id) {
        log.debug("Getting all auction from artist with id {}", id);
        return auctionCollectioneerService.getAllAuctionsByCustomerId(id).stream().map(AuctionMapper.INSTANCE::auctionToAuctionDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}/placeBid")
    public AuctionDto placeBid(@PathVariable Long id, @RequestBody double bidAmount) {
        log.info("Placing a bid for auction with ID: {}. Bid amount: {}", id, bidAmount);
        AuctionDto auction = AuctionMapper.INSTANCE.auctionToAuctionDto(auctionCollectioneerService.placeBid(id, bidAmount));
        log.info("Bid placed for auction with ID: {}. Current bid: {}", auction.getId(), auction.getCurrentBid());
        return auction;
    }

    @GetMapping("/{id}/currentBid")
    public double getCurrentBid(@PathVariable Long id) {
        log.debug("Retrieving current bid for auction with ID: {}", id);
        return auctionCollectioneerService.getCurrentBid(id);
    }

    @GetMapping("/{id}/artwork")
    public ArtworkDto getArtworkFromAuction(@PathVariable Long id) {
        log.debug("Fetching artwork from auction with ID: {}", id);
        ArtworkDto artwork = ArtworkMapper.INSTANCE.artworkToArtworkDto(auctionCollectioneerService.getArtworkFromAuction(id));
        if (artwork != null) {
            log.debug("Retrieved artwork with ID: {} from auction with ID: {}", artwork.getId(), id);
        } else {
            log.warn("Artwork not found for auction with ID: {}", id);
        }
        return artwork;
    }

    @GetMapping("/{id}")
    public AuctionDto getAuctionById(@PathVariable Long id) {
        log.debug("Fetching auction with ID: {}", id);
        Auction auction = auctionCollectioneerService.getAuctionById(id);
        if (auction != null) {
            return AuctionMapper.INSTANCE.auctionToAuctionDto(auction);
        } else {
            log.warn("Auction not found for auction with ID: {}", id);
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
