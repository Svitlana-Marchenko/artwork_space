package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.User;

import java.util.List;

public interface AuctionArtistService {

    Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step);
    double displayCurrentBid(Long id);
    User displayCurrentBuyer(Long id);
    List<Auction> getAllActiveAuctions();
    void closeAuction(Long id);
    Auction updateName (Long id, String name);
    Auction updateDescription(Long id, String newDescription);

}
