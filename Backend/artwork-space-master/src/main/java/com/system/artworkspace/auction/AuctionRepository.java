package com.system.artworkspace.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity,Long> {
   //List<Auction> findByClosingTime(Date currentDate);
}
