package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.Min;
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
public class SaleDto {

    private Long id;

    @NotNull(message = "Soled artwork cant be null")
    private ArtworkDto artwork;

    @NotNull(message = "Buyer of artwork cant be null")
    private UserDto buyer;

    @NotNull(message = "Seller of artwork cant be null")
    private UserDto seller;

    @Min(value = 1, message = "Price must be > 0")
    private double price;

    @NotNull(message = "Date of sale cant be null")
    private Date dateOfBuying;
}
