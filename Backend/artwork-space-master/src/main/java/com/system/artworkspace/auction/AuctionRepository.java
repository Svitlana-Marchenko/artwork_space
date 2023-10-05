package com.system.artworkspace.auction;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction,Long> {
   // List<Auction> findAuctionsByDate(Date date);
   List<Auction> findByEndDateAfter(Date currentDate); // Знайти активні аукціони
    List<Auction> findByArtwork(Artwork artwork); // Знайти аукціони для певної мистецької роботи

}
