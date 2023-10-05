package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.Rating;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;

import java.util.List;

public interface AuctionArtistService {

    Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step);
    double displayCurrenBid(Auction auction);
    User displayCurrenBuyer(Auction auction);
    List<Auction> getAllActiveAuctions();
    void closeAuction(Auction auction);
    void editName (Auction auction, String name);
    void editDescription(Auction auction, String description);

}
