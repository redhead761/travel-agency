package com.epam.finaltask.integration;

import com.epam.finaltask.dto.RequestAmount;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserControllerFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testFindAll() throws Exception {
        addUser("testUser");
        addUser("testUser2");

        mockMvc.perform(get("/users?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setPhoneNumber("+380501234567");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].username", is("testUser")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER","ROLE_ADMIN"})
    void testUpdateUser() throws Exception {
        UserDTO user = addUser("testUser");
        user.setUsername("updatedUserName");
        user.setPhoneNumber("+380509876543");
        user.setPassword("updatedPassword");

        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].username", is("updatedUserName")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER","ROLE_ADMIN"})
    void testGetUser() throws Exception {
        UserDTO user = addUser("testUser");

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].username", is("testUser")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testChangeUserAccountStatus() throws Exception {
        UserDTO user = addUser("testUser");

        mockMvc.perform(patch("/users/" + user.getId() + "/status?accountStatus=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testChangeRole() throws Exception {
        UserDTO user = addUser("testUser");

        mockMvc.perform(patch("/users/" + user.getId() + "/role?role=ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void testBalanceTopUp() throws Exception {
        UserDTO user = addUser("testUser");

        RequestAmount amount = new RequestAmount(50.0);

        mockMvc.perform(patch("/users/" + user.getId() + "/top_up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)));
    }

    private UserDTO addUser(String username) {

        User user = new User();
        user.setUsername(username);
        user.setBalance(0.0);
        user.setPassword("password");
        user.setPhoneNumber("+380501234561");
        user.setAccountStatus(true);
        user.setRole(Role.USER);

        return userMapper.toUserDTO(userRepository.save(user));
    }
}
