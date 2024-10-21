package com.system.artworkspace.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDto {
    private long id;

    @NotNull(message = "Artwork is null")
    private ArtworkDto artwork;

    @DecimalMin(value = "0.0", message = "Value must be in decimal format")
    private double startingPrice;

    @DecimalMin(value = "0.0", message = "Value must be in decimal format")
    private double bid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Date must be in the future")
    private Date closingTime;

    private UserDto user;

    private double currentBid;
}

