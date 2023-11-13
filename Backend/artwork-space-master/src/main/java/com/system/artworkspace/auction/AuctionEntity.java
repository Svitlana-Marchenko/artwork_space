package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Column(length = 2000)
    private String auctionDescription;
    @OneToOne
    private ArtworkEntity artworkEntity;
    @Column(nullable = false)
    private double startingPrice;
    private double step;
    @Temporal(TemporalType.DATE)
    private Date closingTime;
    @ManyToOne
    private UserEntity user;
    private double currentBid;
    public AuctionEntity(){}
    public AuctionEntity(ArtworkEntity artworkEntity, RatingEntity rating, String auctionName, String auctionDescription, double startingPrice, double step) {
        this.title = auctionName;
        this.auctionDescription = auctionDescription;
        this.artworkEntity = artworkEntity;
        this.startingPrice = startingPrice;
        this.step = step;
    }

    @Override
    public String toString() {
        return "Auction{" +
                ", auctionName='" + title+ '\'' +
                ", auctionDescription='" + auctionDescription + '\'' +
                ", artwork=" + artworkEntity +
                ", startingPrice=" + startingPrice +
                ", step=" + step +
                ", closingTime=" + closingTime +
                ", currentBuyer=" + user +
                ", currentPrice=" + currentBid +
                '}';
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity currentBuyer) {
        this.user = currentBuyer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String auctionName) {
        this.title = auctionName;
    }

    public String getAuctionDescription() {
        return auctionDescription;
    }

    public void setAuctionDescription(String auctionDescription) {
        this.auctionDescription = auctionDescription;
    }

    public ArtworkEntity getArtwork() {
        return artworkEntity;
    }

    public void setArtwork(ArtworkEntity artworkEntity) {
        this.artworkEntity = artworkEntity;
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


    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}
