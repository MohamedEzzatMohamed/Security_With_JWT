package com.mezzat.security_with_jwt.data.entity;

import lombok.Data;

@Data
public class RoleToUserForm {
    private String userName;
    private String roleName;
}
