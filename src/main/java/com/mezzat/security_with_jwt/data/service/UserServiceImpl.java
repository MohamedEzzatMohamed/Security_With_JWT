package com.mezzat.security_with_jwt.data.service;

import com.mezzat.security_with_jwt.data.entity.Role;
import com.mezzat.security_with_jwt.data.entity.User;
import com.mezzat.security_with_jwt.data.repository.RoleRepository;
import com.mezzat.security_with_jwt.data.repository.UserRepository;
import com.mezzat.security_with_jwt.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getFirstName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userEmail, String roleName) {
        log.info("Adding role {} to user {}", roleName, userEmail);
        User user = userRepository.findByEmail(userEmail);
        Role role = roleRepository.findByName(roleName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        user.getRoles().add(role);

        userRepository.save(user);
    }

    @Override
    public User getUser(String userEmail) {
        log.info("fetching user {}", userEmail);
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }
}
