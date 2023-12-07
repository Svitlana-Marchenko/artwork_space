package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.UserMapper;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
//@PreAuthorize("hasAuthority('ARTIST')")
public class AuctionArtistController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionArtistController.class);
    private final AuctionArtistService auctionService;

    @Autowired
    public AuctionArtistController(AuctionArtistService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public AuctionDto createAuction(
            @RequestBody @Valid AuctionDto auction, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/{id}/close")
    public void closeAuction(@PathVariable Long id) {
        logger.info("Closing auction with ID: {}", id);
        auctionService.closeAuction(id);
        logger.info("Auction closed with ID: {}", id);
    }

}
