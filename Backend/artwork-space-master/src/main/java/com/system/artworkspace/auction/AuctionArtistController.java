package com.system.artworkspace.auction;

import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserMapper;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
@Slf4j
public class AuctionArtistController {
   
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
        log.info("Creating an auction for artwork with ID: {}", auction.getArtwork().getId());
        return AuctionMapper.INSTANCE.auctionToAuctionDto(auctionService.createAuction(AuctionMapper.INSTANCE.auctionDtoToAuction(auction)));
    }

    @GetMapping("/{id}/currentBid")
    public double displayCurrentBid(@PathVariable Long id) {
        log.debug("Displaying current bid for auction with ID: {}", id);
        return auctionService.displayCurrentBid(id);
    }

    @GetMapping("/{id}/currentBuyer")
    public UserDto displayCurrentBuyer(@PathVariable Long id) {
        log.debug("Displaying current buyer for auction with ID: {}", id);
        User current = auctionService.displayCurrentBuyer(id);
        return UserMapper.INSTANCE.userToUserDto(current);

    }

    @GetMapping("artist/{id}")
    public List<AuctionDto> getAllAuctionsByUserId(@PathVariable Long id){
        log.debug("Getting all auction from artist with id {}", id);
        return auctionService.getAllAuctionsByUserId(id).stream().map(AuctionMapper.INSTANCE::auctionToAuctionDto).collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<AuctionDto> getAllActiveAuctions() {
        log.debug("Fetching all active auctions");
        return auctionService.getAllActiveAuctions().stream().map(AuctionMapper.INSTANCE::auctionToAuctionDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}/close")
    public void closeAuction(@PathVariable Long id) {
        log.info("Closing auction with ID: {}", id);
        auctionService.closeAuction(id);
        log.info("Auction closed with ID: {}", id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
