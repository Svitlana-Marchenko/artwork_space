package com.system.artworkspace.auction.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {
    @Query("SELECT s FROM SaleEntity s WHERE s.seller.id = :sellerId")
    List<SaleEntity> findAllSalesBySellerId(@Param("sellerId") Long sellerId);

    @Query("SELECT s FROM SaleEntity s WHERE s.buyer.id = :buyerId")
    List<SaleEntity> findAllSalesByBuyerId(@Param("buyerId") Long sellerId);

}
