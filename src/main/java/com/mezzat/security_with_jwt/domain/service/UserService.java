package com.mezzat.security_with_jwt.domain.service;

import com.mezzat.security_with_jwt.data.entity.Role;
import com.mezzat.security_with_jwt.data.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String userEmail, String roleName);

    User getUser(String userEmail);

    List<User> getUsers();

}
