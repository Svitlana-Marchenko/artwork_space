package com.system.artworkspace.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class AuctionDto {
    private long id;

    private ArtworkDto artwork;

    @DecimalMin(value = "0.0")
    private double startingPrice;

    @DecimalMin(value = "0.0")
    private double bid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date closingTime;

    private UserDto user;

    private double currentBid;

    public AuctionDto() {
    }

    public AuctionDto(long id, ArtworkDto artwork, double startingPrice, double step, Date closingTime, UserDto currentBuyer, double currentBid) {
        this.id = id;
        this.artwork = artwork;
        this.startingPrice = startingPrice;
        this.bid= step;
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

    public ArtworkDto getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDto artwork) {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto currentBuyer) {
        this.user = currentBuyer;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}

