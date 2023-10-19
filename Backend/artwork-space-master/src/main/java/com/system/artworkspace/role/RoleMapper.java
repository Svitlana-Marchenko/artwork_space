package com.system.artworkspace.role;

import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.rating.RatingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);

    RoleEntity roleToRoleEntity(Role role);

    Role roleEntityToRole(RoleEntity roleEntity);
}

