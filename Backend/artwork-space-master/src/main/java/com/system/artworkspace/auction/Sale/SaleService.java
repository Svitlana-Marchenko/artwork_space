package com.system.artworkspace.auction.Sale;

import java.util.List;

public interface SaleService {

    Sale createSale (Sale sale);

    Sale getSaleById(Long id);

    List<Sale> getAllSales();

    List<Sale> getSalesForArtist(Long id);

    List<Sale> getSalesForCollectioneer(Long id);
}
