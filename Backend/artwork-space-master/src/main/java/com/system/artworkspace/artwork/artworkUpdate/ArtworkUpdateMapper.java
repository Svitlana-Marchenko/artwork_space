package com.system.artworkspace.artwork.artworkUpdate;

import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateDto;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtworkUpdateMapper {

    ArtworkUpdateMapper INSTANCE = Mappers.getMapper(ArtworkUpdateMapper.class);

    ArtworkUpdateDto artworkUpdateToArtworkUpdateDto(ArtworkUpdate artworkUpdate);

    ArtworkUpdate artworkUpdateDtoToArtworkUpdate(ArtworkUpdateDto artworkUpdateDto);

}
