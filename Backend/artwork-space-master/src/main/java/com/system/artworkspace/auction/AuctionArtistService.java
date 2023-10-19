package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;

import java.util.List;

public interface AuctionArtistService {

    Auction createAuction(Artwork artwork, RatingEntity rating, String auctionName, String auctionDescription, double startingPrice, double step);
    double displayCurrentBid(Long id);
    UserEntity displayCurrentBuyer(Long id);
    List<Auction> getAllActiveAuctions();
    void closeAuction(Long id);
    Auction updateName (Long id, String name);
    Auction updateDescription(Long id, String newDescription);

}
