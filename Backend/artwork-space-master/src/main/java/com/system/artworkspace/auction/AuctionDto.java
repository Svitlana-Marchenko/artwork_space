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
    @NotNull
    @Size(max = 50)
    private String title;

    @Size(max = 2000)
    private String description;

    private ArtworkDto artwork;

    @DecimalMin(value = "0.0")
    private double startingPrice;

    @DecimalMin(value = "0.0")
    private double step;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date closingTime;

    private UserDto user;

    private double currentBid;

    public AuctionDto() {
    }

    public AuctionDto(long id, String auctionName, String auctionDescription, ArtworkDto artwork, double startingPrice, double step, Date closingTime, UserDto currentBuyer, double currentBid) {
        this.id = id;
        this.title = auctionName;
        this.description = auctionDescription;
        this.artwork = artwork;
        this.startingPrice = startingPrice;
        this.step = step;
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

