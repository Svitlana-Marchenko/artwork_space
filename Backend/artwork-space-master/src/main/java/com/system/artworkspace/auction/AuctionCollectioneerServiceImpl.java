package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class AuctionCollectioneerServiceImpl implements AuctionCollectioneerService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    public List<Auction> getAvailableAuctions() {
        Date currentDate = new Date();
        Example<Auction> example = Example.of(new Auction(), ExampleMatcher.matchingAll().withIgnorePaths("closingTime"));
        return auctionRepository.findAll(example);
    }

    @Override
    public Auction placeBid(Auction auction, double bidAmount) {
        return auctionRepository.save(auction);
    }

    @Override
    public double getCurrentBid(Auction auction) {
        return auction.getCurrentBid();
    }

    @Override
    public Artwork getArtworkFromAuction(Auction auction) {
        Long artworkId = auction.getId();
        return artworkRepository.findById(artworkId).orElse(null);
    }
}
