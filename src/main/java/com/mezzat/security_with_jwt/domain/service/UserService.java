package com.mezzat.security_with_jwt.domain.service;

import com.mezzat.security_with_jwt.domain.dto.RoleDto;
import com.mezzat.security_with_jwt.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    RoleDto saveRole(RoleDto roleDto);

    void addRoleToUser(String userEmail, String roleName);

    UserDto getUser(String userEmail);

    List<UserDto> getUsers();

}
