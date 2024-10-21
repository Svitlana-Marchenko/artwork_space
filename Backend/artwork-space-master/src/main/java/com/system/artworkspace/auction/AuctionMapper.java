package com.system.artworkspace.auction;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);

    AuctionDto auctionToAuctionDto(Auction auction);

    Auction auctionDtoToAuction(AuctionDto auctionDto);

    AuctionEntity auctionToAuctionEntity(Auction auction);

    Auction auctionEntityToAuction(AuctionEntity auctionEntity);
}
