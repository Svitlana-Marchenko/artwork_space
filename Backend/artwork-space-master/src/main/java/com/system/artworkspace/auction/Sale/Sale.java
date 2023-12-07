package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.Date;

public class Sale {
    private Long id;
    private Artwork artwork;
    private User buyer;
    private User seller;
    private double price;
    private Date dateOfBuying;

    public Sale(){}

    public Sale(Long id, Artwork artwork, User buyer, User seller, double price, Date dateOfBuying) {
        this.id = id;
        this.artwork = artwork;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.dateOfBuying = dateOfBuying;
    }

    public Sale(Artwork artwork, User buyer, User seller, double price, Date dateOfBuying) {
        this.artwork = artwork;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.dateOfBuying = dateOfBuying;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateOfBuying() {
        return dateOfBuying;
    }

    public void setDateOfBuying(Date dateOfBuying) {
        this.dateOfBuying = dateOfBuying;
    }
}
