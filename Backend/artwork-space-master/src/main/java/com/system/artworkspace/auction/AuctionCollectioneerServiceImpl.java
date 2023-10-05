package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {

    private AuctionRepository repository;

    @Autowired
    public AuctionCollectioneerServiceImpl(AuctionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Auction> getAvailableAuctions() {
        return repository.findAll();
    }

    @Override
    public void placeBid(Auction auction, double bidAmount, User user) {

        if (bidAmount > auction.getCurrentBid()) {
            auction.setCurrentBid(bidAmount);
            auction.setCurrentBuyer(user);

            repository.save(auction);
        } else {
            throw new RuntimeException("Bid amount is too low. Current highest bid: " + auction.getCurrentBid());
        }
    }

    @Override
    public double getCurrentBid(Auction auction) {
        return auction.getCurrentBid();
    }

    @Override
    public Artwork getArtworkFromAuction(Auction auction) {
        return auction.getArtwork();
    }
}
