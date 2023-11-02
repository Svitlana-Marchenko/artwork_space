package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.Date;

public class Auction {
    private long id;

    private String auctionName;

    private String auctionDescription;

    private Artwork artwork;

    private double startingPrice;

    private double step;

    private Date closingTime;

    private User currentBuyer;

    private double currentBid;

    public Auction() {
    }

    public Auction(long id, String auctionName, String auctionDescription, Artwork artwork, double startingPrice, double step, Date closingTime, User currentBuyer, double currentBid) {
        this.id = id;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.artwork = artwork;
        this.startingPrice = startingPrice;
        this.step = step;
        this.closingTime = closingTime;
        this.currentBuyer = currentBuyer;
        this.currentBid = currentBid;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getCurrentBuyer() {
        return currentBuyer;
    }

    public void setCurrentBuyer(User currentBuyer) {
        this.currentBuyer = currentBuyer;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public boolean isClosed() {
        Date currentDate = new Date();
        return closingTime != null && currentDate.after(closingTime);
    }
}

