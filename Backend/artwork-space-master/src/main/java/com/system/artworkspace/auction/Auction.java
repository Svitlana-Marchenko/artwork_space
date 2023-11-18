package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.Date;
public class Auction {
    private long id;

    private Artwork artwork;

    private double startingPrice;

    private double bid;

    private Date closingTime;
    private User user;

    private double currentBid;

    public Auction() {
    }

    public Auction(long id, Artwork artwork, double startingPrice, double step, Date closingTime, User currentBuyer, double currentBid) {
        this.id = id;
        this.artwork = artwork;
        this.startingPrice = startingPrice;
        this.bid = step;
        this.closingTime = closingTime;
        this.user = currentBuyer;
        this.currentBid = currentBid;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getBid() {
        return bid;
    }

    public void setBid(double step) {
        this.bid = step;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User currentBuyer) {
        this.user = currentBuyer;
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

