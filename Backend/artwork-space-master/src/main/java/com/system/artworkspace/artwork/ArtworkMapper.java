package com.system.artworkspace.artwork;

import com.system.artworkspace.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface ArtworkMapper {

    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);

    ArtworkDto artworkToArtworkDto(Artwork artwork);

    Artwork artworkDtoToArtwork(ArtworkDto artworkDto);

    ArtworkEntity artworkToArtworkEntity(Artwork artwork);

    Artwork artworkEntityToArtwork(ArtworkEntity artworkEntity);

}
