package com.system.artworkspace.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity,Long> {
    @Modifying
    @Query("DELETE FROM AuctionEntity a WHERE a.closingTime = :closingDate AND a.user IS NULL")
    void deleteClosingTodayWithNoBuyer(@Param("closingDate") Date closingDate);

}
