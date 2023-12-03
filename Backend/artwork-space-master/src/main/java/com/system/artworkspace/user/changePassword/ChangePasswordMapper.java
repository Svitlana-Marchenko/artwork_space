package com.system.artworkspace.user.changePassword;

import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChangePasswordMapper {
    ChangePasswordMapper INSTANCE = Mappers.getMapper(ChangePasswordMapper.class);

    ChangePassword changePasswordDTOToChangePassword(ChangePasswordDTO changePasswordDTO);

    ChangePasswordDTO changePasswordToChangePasswordDTO(ChangePassword changePassword);

}
