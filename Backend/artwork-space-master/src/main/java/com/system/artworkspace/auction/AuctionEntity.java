package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuctionEntity {

    @Getter
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

    public AuctionEntity(ArtworkEntity artworkEntity, double startingPrice, double step, Date closingTime, UserEntity user, double currentBid) {
        this.artworkEntity = artworkEntity;
        this.startingPrice = startingPrice;
        this.bid = step;
        this.closingTime=closingTime;
        this.user=user;
        this.currentBid = currentBid;
    }

    public ArtworkEntity getArtwork() {
        return artworkEntity;
    }

    public void setArtwork(ArtworkEntity artworkEntity) {
        this.artworkEntity = artworkEntity;
    }
}
