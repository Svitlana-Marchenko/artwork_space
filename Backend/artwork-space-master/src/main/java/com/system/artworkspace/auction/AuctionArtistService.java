package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserEntity;

import java.util.List;

public interface AuctionArtistService {

    Auction createAuction (Auction auction);
    Auction getAuctionById (Long id);
    double displayCurrentBid(Long id);
    User displayCurrentBuyer(Long id);
    List<Auction> getAllActiveAuctions();
    void closeAuction(Long id);
    void deleteAuctionById(Long id);
    List<Auction> getAllAuctionsByUserId(Long id);
}
