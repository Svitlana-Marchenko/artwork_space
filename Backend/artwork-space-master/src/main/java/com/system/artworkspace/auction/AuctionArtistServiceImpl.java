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

    @Autowired
    private CountPriceService countPriceService;

    @Override
    public Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        if(startingPrice==0)
            startingPrice=countPriceService.calculatePrice(artwork.getImageSize(),rating.getRate());
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
    public void editName(Auction auction) {

    }

    @Override
    public void editDescription(Auction auction) {

    }
}
