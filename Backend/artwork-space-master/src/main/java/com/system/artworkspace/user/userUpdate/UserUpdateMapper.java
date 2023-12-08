package com.system.artworkspace.user.userUpdate;

import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateDto;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    UserUpdateMapper INSTANCE = Mappers.getMapper(UserUpdateMapper.class);

    UserUpdateDto userUpdateToUserUpdateDto(UserUpdate userUpdate);

    UserUpdate userUpdateDtoToUserUpdate(UserUpdateDto userUpdateDto);


}
