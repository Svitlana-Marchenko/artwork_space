package com.system.artworkspace.auction.Sale;

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
