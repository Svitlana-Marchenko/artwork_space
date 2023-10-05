package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Override
    public List<Auction> getAvailableAuctions() {
        return null;
    }

    @Override
    public Auction placeBid(Auction auction, double bidAmount) {
        return null;
    }

    @Override
    public double getCurrentBid(Auction auction) {
        return 0;
    }

    @Override
    public Artwork getArtworkFromAuction(Auction auction) {
        return null;
    }
}
