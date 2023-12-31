package com.system.artworkspace.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

//TODO add validation if there is already auction for this artwork
public class AuctionDto {
    private long id;

    @NotNull(message = "Artwork is null")
    private ArtworkDto artwork;

    @DecimalMin(value = "0.0", message = "Value must be in decimal format")
    private double startingPrice;

    @DecimalMin(value = "0.0", message = "Value must be in decimal format")
    private double bid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Date must be in the future")
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

