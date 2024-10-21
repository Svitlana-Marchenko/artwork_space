package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import java.util.List;

public interface AuctionCollectioneerService {

    List<Auction> getAvailableAuctions();

    Auction placeBid(Long id, double bidAmount);

    double getCurrentBid(Long id);

    Artwork getArtworkFromAuction (Long id);

    Auction getAuctionByPaintingId(Long paintingId);

    Auction getAuctionById(Long id);

    List<Auction> getAllAuctionsByCustomerId(Long id);
}
