package com.system.artworkspace.auction;

import com.system.artworkspace.user.User;

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
