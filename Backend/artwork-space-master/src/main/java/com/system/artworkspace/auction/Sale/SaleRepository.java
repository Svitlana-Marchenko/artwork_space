package com.system.artworkspace.auction.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {

    List<SaleEntity> findAllBySellerId(Long sellerId);

    List<SaleEntity> findAllByBuyerId(Long id);

    boolean existsSaleEntityByArtworkId(Long id);

}
