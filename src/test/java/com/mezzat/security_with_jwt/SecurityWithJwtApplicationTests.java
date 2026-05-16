package com.mezzat.security_with_jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mezzat.security_with_jwt.data.repository.RoleRepository;
import com.mezzat.security_with_jwt.data.repository.UserRepository;
import com.mezzat.security_with_jwt.domain.dto.RoleDto;
import com.mezzat.security_with_jwt.domain.dto.UserDto;
import com.mezzat.security_with_jwt.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void setUp() throws Exception {

        // تنظيف البيانات
        userRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();

        // إنشاء Roles
        userService.saveRole(new RoleDto("ROLE_USER"));
        userService.saveRole(new RoleDto("ROLE_ADMIN"));

        // إنشاء User
        userService.saveUser(
                new UserDto(
                        "test@example.com",
                        "test",
                        "user",
                        "password",
                        new ArrayList<>()
                )
        );

        // إضافة Roles للمستخدم
        userService.addRoleToUser("test@example.com", "ROLE_USER");
        userService.addRoleToUser("test@example.com", "ROLE_ADMIN");

        // Login للحصول على JWT
        MvcResult result = mockMvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("email", "test@example.com")
                                .param("password", "password")
                )
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        accessToken = objectMapper.readTree(response)
                .get("access_token")
                .asText();

        refreshToken = objectMapper.readTree(response)
                .get("refresh_token")
                .asText();

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }

    @Test
    void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowAccessToAuthenticatedUsers() throws Exception {

        mockMvc.perform(
                        get("/api/users")
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateUser() throws Exception {

        UserDto user = new UserDto(
                "new@example.com",
                "new",
                "user",
                "password",
                new ArrayList<>()
        );

        mockMvc.perform(
                        post("/api/user/save")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateUserWithInvalidData() throws Exception {

        UserDto user = new UserDto(
                "invalid",
                null,
                "user",
                "password",
                new ArrayList<>()
        );

        mockMvc.perform(
                        post("/api/user/save")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRefreshToken() throws Exception {

        mockMvc.perform(
                        post("/api/token/refresh")
                                .header("Authorization", "Bearer " + refreshToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRefreshTokenWithInvalidToken() throws Exception {

        mockMvc.perform(
                        post("/api/token/refresh")
                                .header("Authorization", "Bearer invalid_token")
                )
                .andExpect(status().isForbidden());
    }
}