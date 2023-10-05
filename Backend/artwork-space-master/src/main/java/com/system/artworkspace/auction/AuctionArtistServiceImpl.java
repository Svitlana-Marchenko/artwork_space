package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.Rating;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;
import org.rating.CountPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionArtistServiceImpl implements AuctionArtistService {

    @Autowired
    private CountPriceService countPriceService;

    @Autowired
    private AuctionRepository repository;


    @Override
    public Auction createAuction(Artwork artwork, Rating rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        if(startingPrice==0)
            startingPrice=countPriceService.calculatePrice(artwork.getImageSize(),rating.getRate());
        return null;
    }

    @Override
    public double displayCurrenBid(Auction auction) {
        return auction.getCurrentBid();
    }

    @Override
    public User displayCurrenBuyer(Auction auction) {
        return auction.getCurrentBuyer();
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        return repository.findAll();
    }

    //todo чи залишаємо таку штуку, чи не робимо цей функціонал
    @Override
    public void closeAuction(Auction auction) {

    }

    @Override
    public void editName(Auction auction, String name) {
        Auction existingAuction = repository.findById(auction.getId()).orElse(null);

        if (existingAuction != null) {
            existingAuction.setAuctionName(name);
            repository.save(existingAuction);
        } else {
            throw new RuntimeException("Аукціон не знайдено з ID: " + auction.getId());
        }
    }

    @Override
    public void editDescription(Auction auction, String description) {

        Auction existingAuction = repository.findById(auction.getId()).orElse(null);

        if (existingAuction != null) {

            existingAuction.setAuctionDescription(description);

            repository.save(existingAuction);
        } else {
            throw new RuntimeException("Аукціон не знайдено з ID: " + auction.getId());
        }
    }
}
