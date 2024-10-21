package com.system.artworkspace.exhibition;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExhibitionMapper {

    ExhibitionMapper INSTANCE = Mappers.getMapper(ExhibitionMapper.class);

    ExhibitionDto exhibitionToExhibitionDto(Exhibition exhibition);

    Exhibition exhibitionDtoToExhibition(ExhibitionDto exhibitionDto);

    ExhibitionEntity exhibitionToExhibitionEntity(Exhibition exhibition);

    Exhibition exhibitionEntityToExhibition(ExhibitionEntity exhibitionEntity);
}
