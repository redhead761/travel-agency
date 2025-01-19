package com.epam.finaltask.integration;

import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.JwtBlackListRepository;
import com.epam.finaltask.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtBlackListRepository jwtBlacklistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        jwtBlacklistRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setAccountStatus(true);
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Test
    void testFullAuthenticationFlow() throws Exception {
        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "testUser",
                                    "password": "password"
                                }
                                """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.succeeded").value(true))
                .andExpect(jsonPath("$.results[0].token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = JsonPath.parse(loginResponse).read("$.results[0].token");

        mockMvc.perform(post("/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        assertTrue(jwtBlacklistRepository.existsById(token));
    }
}
