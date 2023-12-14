package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private ArtworkEntity artwork;
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity buyer;
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity seller;
    @Column(nullable = false)
    private double price;
    @Temporal(TemporalType.DATE)
    private Date dateOfBuying;

    public SaleEntity(){}

    public SaleEntity(Long id, ArtworkEntity artwork, UserEntity buyer, UserEntity seller, double price, Date dateOfBuying) {
        this.id = id;
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

    public ArtworkEntity getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
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
