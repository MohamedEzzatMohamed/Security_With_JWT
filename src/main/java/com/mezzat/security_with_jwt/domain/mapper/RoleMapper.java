package com.mezzat.security_with_jwt.domain.mapper;

import com.mezzat.security_with_jwt.data.entity.Role;
import com.mezzat.security_with_jwt.domain.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
