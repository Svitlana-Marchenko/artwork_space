package com.system.artworkspace.user.userUpdate;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    UserUpdateMapper INSTANCE = Mappers.getMapper(UserUpdateMapper.class);

    UserUpdateDto userUpdateToUserUpdateDto(UserUpdate userUpdate);

    UserUpdate userUpdateDtoToUserUpdate(UserUpdateDto userUpdateDto);
}
