package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String auctionName;
    private String auctionDescription;
    @OneToOne
    private Artwork artwork;
    private double startingPrice;
    private double step;
    @Temporal(TemporalType.DATE)
    private Date closingTime;
    @ManyToOne
    private User currentBuyer;
    private double currentBid;
   // @Autowired
    //private boolean dateFormatResult;

    @Override
    public String toString() {
        return "Auction{" +
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCurrentBuyer() {
        return currentBuyer;
    }

    public void setCurrentBuyer(User currentBuyer) {
        this.currentBuyer = currentBuyer;
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
