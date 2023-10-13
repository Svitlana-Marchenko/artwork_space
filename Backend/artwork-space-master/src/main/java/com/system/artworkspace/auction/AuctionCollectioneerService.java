package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

import java.util.List;

public interface AuctionCollectioneerService {
    List<AuctionDto> getAvailableAuctions();

    AuctionDto placeBid(AuctionDto auction, double bidAmount);

    double getCurrentBid(AuctionDto auction);

    ArtworkDto getArtworkFromAuction (AuctionDto auction);
}
