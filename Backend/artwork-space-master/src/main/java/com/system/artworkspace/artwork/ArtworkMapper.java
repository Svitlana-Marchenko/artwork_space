package com.system.artworkspace.artwork;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtworkMapper {

    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);

    ArtworkDto artworkToArtworkDto(Artwork artwork);

    Artwork artworkDtoToArtwork(ArtworkDto artworkDto);

    ArtworkEntity artworkToArtworkEntity(Artwork artwork);

    Artwork artworkEntityToArtwork(ArtworkEntity artworkEntity);

}
