package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private ArtworkEntity artworkEntity;
    @Column(nullable = false)
    private double startingPrice;
    private double bid;
    @Temporal(TemporalType.DATE)
    private Date closingTime;
    @ManyToOne
    private UserEntity user;
    private double currentBid;
    public AuctionEntity(){}
    public AuctionEntity(ArtworkEntity artworkEntity, double startingPrice, double step, Date closingTime, UserEntity user, double currentBid) {
         this.artworkEntity = artworkEntity;
        this.startingPrice = startingPrice;
        this.bid = step;
        this.closingTime=closingTime;
        this.user=user;
        this.currentBid = currentBid;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "artwork=" + artworkEntity +
                ", startingPrice=" + startingPrice +
                ", step=" + bid +
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


    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
}
