package com.system.artworkspace.rating;

import com.system.artworkspace.exhibition.Exhibition;
import com.system.artworkspace.exhibition.ExhibitionDto;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDto ratingToRatingDto(Rating rating);

    Rating ratingDtoToRating(RatingDto ratingDto);

    RatingEntity ratingToRatingEntity(Rating rating);

    Rating ratingEntityToRating(RatingEntity ratingEntity);
}
