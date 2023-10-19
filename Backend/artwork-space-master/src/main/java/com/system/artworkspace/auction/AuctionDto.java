package com.system.artworkspace.auction;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class AuctionDto {
    private long id;
    @NotNull
    @Size(max = 50)
    private String auctionName;

    @Size(max = 2000)
    private String auctionDescription;

    private long artworkId;

    @DecimalMin(value = "0.0")
    private double startingPrice;

    @DecimalMin(value = "0.0")
    private double step;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date closingTime;

    private Long currentBuyerId;

    private double currentBid;

    public AuctionDto() {
    }

    public AuctionDto(long id, String auctionName, String auctionDescription, long artworkId, double startingPrice, double step, Date closingTime, Long currentBuyerId, double currentBid) {
        this.id = id;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.artworkId = artworkId;
        this.startingPrice = startingPrice;
        this.step = step;
        this.closingTime = closingTime;
        this.currentBuyerId = currentBuyerId;
        this.currentBid = currentBid;
    }

    public AuctionEntity convertToAuction(){
        return new AuctionEntity();
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

    public long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(long artworkId) {
        this.artworkId = artworkId;
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

    public Long getCurrentBuyerId() {
        return currentBuyerId;
    }

    public void setCurrentBuyerId(Long currentBuyerId) {
        this.currentBuyerId = currentBuyerId;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}

