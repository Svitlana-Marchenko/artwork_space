package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.Rating;
import com.system.artworkspace.user.Collectioneer;
import org.rating.CountPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {

    @Override
    public Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        return null;
    }

    @Override
    public double displayCurrenBid(Auction auction) {
        return 0;
    }

    @Override
    public Collectioneer displayCurrenBuyer(Auction auction) {
        return null;
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        return null;
    }

    @Override
    public void closeAuction(Auction auction) {

    }

    @Override
    public Auction updateName(Auction auction) {
        return null;
    }

    @Override
    public Auction updateDescription(Auction auction) {
        return null;
    }
}
