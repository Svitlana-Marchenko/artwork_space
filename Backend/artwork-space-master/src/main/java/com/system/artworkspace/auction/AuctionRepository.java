package com.system.artworkspace.auction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity,Long> {
     @Query("SELECT a FROM AuctionEntity a WHERE a.closingTime = :closingDate AND a.user IS NULL")
    List<AuctionEntity> findClosingTodayWithNoBuyer(@Param("closingDate") Date closingDate, Pageable pageable);

    @Query("SELECT a FROM AuctionEntity a WHERE a.closingTime = :closingDate")
    List<AuctionEntity> findClosingToday(@Param("closingDate") Date closingDate, Pageable pageable);

}
