package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Collectioneer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
public class Auction {
    private String auctionId;
    private String auctionName;
    private String auctionDescription;
    private Artwork artwork;

    private double startingPrice;
    private double step;
    private Date closingTime;
    private Collectioneer currentBuyer;
    private double currentBid;
    @Autowired
    private boolean dateFormatResult;

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId='" + auctionId + '\'' +
                ", auctionName='" + auctionName + '\'' +
                ", auctionDescription='" + auctionDescription + '\'' +
                ", artwork=" + artwork +
                ", startingPrice=" + startingPrice +
                ", step=" + step +
                ", closingTime=" + closingTime +
                ", currentBuyer=" + currentBuyer +
                ", currentPrice=" + currentBid +
                '}';
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getAuctionDescription() {
        return auctionDescription;
    }

    public void setAuctionDescription(String auctionDescription) {
        this.auctionDescription = auctionDescription;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public Collectioneer getCurrentBuyer() {
        return currentBuyer;
    }

    public void setCurrentBuyer(Collectioneer currentBuyer) {
        this.currentBuyer = currentBuyer;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}
