package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.Rating;
import com.system.artworkspace.user.Collectioneer;

import java.util.List;

public interface AuctionArtistService {

    Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step);
    double displayCurrenBid(Auction auction);
    Collectioneer displayCurrenBuyer(Auction auction);
    List<Auction> getAllActiveAuctions();
    void closeAuction(Auction auction);
    Auction updateName (Auction auction, String name);
    Auction updateDescription(Auction auction, String newDescription);

}
