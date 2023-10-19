package com.system.artworkspace.user;

import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.rating.RatingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);
}
