package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;

import java.util.List;

public interface AuctionCollectioneerService {
    List<Auction> getAvailableAuctions();

    void placeBid(Auction auction, double bidAmount);

    double getCurrentBid(Auction auction);

    Artwork getArtworkFromAuction (Auction auction);
}
