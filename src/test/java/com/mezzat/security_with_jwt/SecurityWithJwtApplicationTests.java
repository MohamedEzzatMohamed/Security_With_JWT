package com.mezzat.security_with_jwt;

import com.mezzat.security_with_jwt.data.entity.User;
import com.mezzat.security_with_jwt.data.repository.RoleRepository;
import com.mezzat.security_with_jwt.data.repository.UserRepository;
import com.mezzat.security_with_jwt.domain.dto.RoleDto;
import com.mezzat.security_with_jwt.domain.dto.UserDto;
import com.mezzat.security_with_jwt.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SecurityWithJwtApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create a user and role for testing
        userService.saveRole(new RoleDto("ROLE_USER"));
        userService.saveUser(new UserDto("test@example.com", "test", "user", "password", new ArrayList<>()));
        userService.addRoleToUser("test@example.com", "ROLE_USER");

        // Login to get access token
        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        accessToken = new ObjectMapper().readTree(response).get("access_token").asText();
        refreshToken = new ObjectMapper().readTree(response).get("refresh_token").asText();
        log.info("Access token: {}", accessToken);
        log.info("Refresh token: {}", refreshToken);
    }

    @Test
    void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDto user = new UserDto("new@example.com", "new", "user", "password", new ArrayList<>());
        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateUserWithInvalidData() throws Exception {
        UserDto user = new UserDto("invalid", null, "user", "password", new ArrayList<>());
        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRefreshToken() throws Exception {
        mockMvc.perform(post("/api/token/refresh")
                        .header("Authorization", "Bearer " + refreshToken))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRefreshTokenWithInvalidToken() throws Exception {
        mockMvc.perform(post("/api/token/refresh")
                        .header("Authorization", "Bearer " + "invalid_token"))
                .andExpect(status().isForbidden());
    }
}
