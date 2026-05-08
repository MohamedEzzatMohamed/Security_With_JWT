package com.mezzat.security_with_jwt.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class RoleDto {
    @NotEmpty(message = "Please provide a role name")
    private String name;
}
