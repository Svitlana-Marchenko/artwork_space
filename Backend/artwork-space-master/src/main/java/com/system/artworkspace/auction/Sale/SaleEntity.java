package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    public SaleEntity(ArtworkEntity artwork, UserEntity buyer, UserEntity seller, double price, Date dateOfBuying) {
        this.artwork = artwork;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.dateOfBuying = dateOfBuying;
    }
}
