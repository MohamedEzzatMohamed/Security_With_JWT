package com.mezzat.security_with_jwt.data.repository;

import com.mezzat.security_with_jwt.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
