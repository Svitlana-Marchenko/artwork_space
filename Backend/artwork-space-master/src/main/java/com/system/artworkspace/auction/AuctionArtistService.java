package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;

import java.util.List;

public interface AuctionArtistService {

    AuctionDto createAuction(ArtworkDto artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step);
    double displayCurrentBid(AuctionDto auction);
    User displayCurrentBuyer(AuctionDto auction);
    List<AuctionDto> getAllActiveAuctions();
    void closeAuction(AuctionDto auction);
    AuctionDto updateName (AuctionDto auction, String name);
    AuctionDto updateDescription(AuctionDto auction, String newDescription);

}
