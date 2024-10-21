package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    private long id;

    private Artwork artwork;

    private double startingPrice;

    private double bid;

    private Date closingTime;

    private User user;

    private double currentBid;

    public boolean isClosed() {
        Date currentDate = new Date();
        return closingTime != null && currentDate.after(closingTime);
    }
}

