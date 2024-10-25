package com.system.artworkspace.utils;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionEntity;

import java.util.Date;

import static com.system.artworkspace.utils.Utils.getRandomLong;

public class AuctionUtils {

    public static Auction getRandomAuction() {
        return new Auction(getRandomLong(), ArtworkUtils.getRandomArtwork(), 100.0, 10, new Date(), null, 100);
    }

    public static AuctionEntity getRandomAuctionEntity() {
        return new AuctionEntity(getRandomLong(), ArtworkUtils.getRandomArtworkEntity(), 100.0, 10, new Date(), null, 100);
    }

}
