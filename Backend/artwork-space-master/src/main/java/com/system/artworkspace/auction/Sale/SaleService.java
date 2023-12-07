package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.user.User;

import java.util.List;

public interface SaleService {
    Sale createSale (Sale sale);
    Sale getSaleById(Long id);
    List<Sale> getAllSales();
}
