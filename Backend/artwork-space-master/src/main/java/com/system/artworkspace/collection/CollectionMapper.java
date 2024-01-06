package com.system.artworkspace.collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    CollectionDto collectionToCollectionDto(Collection auction);

    Collection collectionDtoToCollection(CollectionDto auctionDto);

    CollectionEntity collectionToCollectionEntity(Collection auction);

    Collection collectionEntityToCollection(CollectionEntity auctionEntity);
}
