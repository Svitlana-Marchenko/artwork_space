package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.Rating;
import com.system.artworkspace.user.Collectioneer;
import org.rating.CountPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Override
    public Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        Auction auction = new Auction(artwork, rating, auctionName, auctionDescription, startingPrice, step);
        return auctionRepository.save(auction);
    }

    @Override
    public double displayCurrenBid(Auction auction) {
        return auction.getCurrentBid();
    }

    @Override
    public Collectioneer displayCurrenBuyer(Auction auction) {
        return (Collectioneer) auction.getCurrentBuyer();
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        Date currentDate = new Date();
        return auctionRepository.findByClosingTime(currentDate);
    }

    @Override
    public void closeAuction(Auction auction) {
        //auction.setClosed(true);
        auctionRepository.save(auction);
    }

    @Override
    public Auction updateName(Auction auction, String name) {
        auction.setAuctionName(name);
        return auctionRepository.save(auction);
    }

    @Override
    public Auction updateDescription(Auction auction, String newDescription) {
        auction.setAuctionDescription(newDescription);
        return auctionRepository.save(auction);
    }
}
