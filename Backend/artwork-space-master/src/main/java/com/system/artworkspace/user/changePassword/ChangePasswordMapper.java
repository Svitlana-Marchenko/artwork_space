package com.system.artworkspace.user.changePassword;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChangePasswordMapper {

    ChangePasswordMapper INSTANCE = Mappers.getMapper(ChangePasswordMapper.class);

    ChangePassword changePasswordDTOToChangePassword(ChangePasswordDTO changePasswordDTO);

    ChangePasswordDTO changePasswordToChangePasswordDTO(ChangePassword changePassword);

}
