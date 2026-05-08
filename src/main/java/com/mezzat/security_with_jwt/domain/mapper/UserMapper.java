package com.mezzat.security_with_jwt.domain.mapper;

import com.mezzat.security_with_jwt.data.entity.User;
import com.mezzat.security_with_jwt.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
