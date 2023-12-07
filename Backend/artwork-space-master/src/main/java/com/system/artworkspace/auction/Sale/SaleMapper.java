package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionDto;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    SaleDto saleToSaleDto(Sale sale);

    Sale saleDtoToSale(SaleDto saleDto);

    SaleEntity saleToSaleEntity(Sale sale);

    Sale saleEntityToSale(SaleEntity saleEntity);
}
