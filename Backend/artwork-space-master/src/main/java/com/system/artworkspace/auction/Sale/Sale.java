package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private Long id;
    private Artwork artwork;
    private User buyer;
    private User seller;
    private double price;
    private Date dateOfBuying;

    public Sale(Artwork artwork, User buyer, User seller, double price, Date dateOfBuying) {
        this.artwork = artwork;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.dateOfBuying = dateOfBuying;
    }
}
