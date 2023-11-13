package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.Date;

public class Auction {
    private long id;

    private String title;

    private String description;

    private Artwork artwork;

    private double startingPrice;

    private double step;

    private Date closingTime;

    private User currentBuyer;

    private double currentBid;

    public Auction() {
    }

    public Auction(long id, String title, String description, Artwork artwork, double startingPrice, double step, Date closingTime, User currentBuyer, double currentBid) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return currentBuyer;
    }

    public void setUser(User currentBuyer) {
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

