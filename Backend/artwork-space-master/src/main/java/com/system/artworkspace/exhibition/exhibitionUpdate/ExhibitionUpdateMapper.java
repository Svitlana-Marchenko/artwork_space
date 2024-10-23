package com.system.artworkspace.exhibition.exhibitionUpdate;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExhibitionUpdateMapper {

    ExhibitionUpdateMapper INSTANCE = Mappers.getMapper(ExhibitionUpdateMapper.class);

    ExhibitionUpdateDto exhibitionUpdateToExhibitionUpdateDto(ExhibitionUpdate exhibitionUpdate);

    ExhibitionUpdate exhibitionUpdateDtoToExhibitionUpdate(ExhibitionUpdateDto exhibitionUpdateDto);
}
