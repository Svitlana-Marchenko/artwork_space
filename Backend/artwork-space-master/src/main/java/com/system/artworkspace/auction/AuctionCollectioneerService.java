package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.List;

public interface AuctionCollectioneerService {
    List<Auction> getAvailableAuctions();

    void placeBid(Auction auction, double bidAmount, User user);

    double getCurrentBid(Auction auction);

    Artwork getArtworkFromAuction (Auction auction);
}
