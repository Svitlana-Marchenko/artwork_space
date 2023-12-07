package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class SaleDto {
    private Long id;
    @NotNull(message = "Soled artwork cant be null")
    private ArtworkDto artwork;
    @NotNull(message = "Buyer of artwork cant be null")
    private UserDto buyer;
    @NotNull(message = "Seller of artwork cant be null")
    private UserDto seller;
    @NotBlank(message = "Price cant be blank")
    @Min(value = 1, message = "Price must be > 0")
    private double price;
    @NotNull(message = "Date of sale cant be null")
    private Date dateOfBuying;

    public SaleDto(){}

    public SaleDto(Long id, ArtworkDto artwork, UserDto buyer, UserDto seller, double price, Date dateOfBuying) {
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

    public ArtworkDto getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDto artwork) {
        this.artwork = artwork;
    }

    public UserDto getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDto buyer) {
        this.buyer = buyer;
    }

    public UserDto getSeller() {
        return seller;
    }

    public void setSeller(UserDto seller) {
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
