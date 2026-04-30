package com.mezzat.security_with_jwt.data.repository;

import com.mezzat.security_with_jwt.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
