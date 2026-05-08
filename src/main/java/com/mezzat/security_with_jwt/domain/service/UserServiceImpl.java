package com.mezzat.security_with_jwt.domain.service;

import com.mezzat.security_with_jwt.data.entity.Role;
import com.mezzat.security_with_jwt.data.entity.User;
import com.mezzat.security_with_jwt.data.repository.RoleRepository;
import com.mezzat.security_with_jwt.data.repository.UserRepository;
import com.mezzat.security_with_jwt.domain.dto.RoleDto;
import com.mezzat.security_with_jwt.domain.dto.UserDto;
import com.mezzat.security_with_jwt.domain.mapper.RoleMapper;
import com.mezzat.security_with_jwt.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto saveUser(UserDto userDto) {
        log.info("Saving new user {} to the database", userDto.getFirstName());
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        log.info("Saving new role {} to the database", roleDto.getName());
        Role role = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public void addRoleToUser(String userEmail, String roleName) {
        log.info("Adding role {} to user {}", roleName, userEmail);
        if (userEmail == null) {
            throw new RuntimeException("User not found");
        }

        if (roleName == null) {
            throw new RuntimeException("Role not found");
        }
        User user = userRepository.findByEmail(userEmail);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserDto getUser(String userEmail) {
        log.info("fetching user {}", userEmail);
        return userMapper.toDto(userRepository.findByEmail(userEmail));
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("fetching all users");

        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database: {}", username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
