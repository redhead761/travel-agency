package com.epam.finaltask.controller.impl;

import com.epam.finaltask.dto.RequestAmount;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.service.LocalizationService;
import com.epam.finaltask.service.UserService;
import com.epam.finaltask.dto.RemoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerImplTest {

    @InjectMocks
    private UserControllerImpl userController;

    @Mock
    private UserService userService;

    @Mock
    private LocalizationService localizationService;

    private UserDTO userDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .username("testUser")
                .password("password")
                .role(Role.USER.name())
                .build();
        userId = UUID.randomUUID();
    }

    @Test
    void testFindAll() {
        Page<UserDTO> userPage = new PageImpl<>(List.of(userDto));

        when(userService.findAll(1, 10)).thenReturn(userPage);
        when(localizationService.getMessage("users.obtained")).thenReturn("Users obtained");

        RemoteResponse response = userController.findAll(1, 10);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Users obtained", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testRegisterUser() {
        when(userService.register(userDto)).thenReturn(userDto);
        when(localizationService.getMessage("users.registered")).thenReturn("User registered");

        RemoteResponse response = userController.registerUser(userDto);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusCode());
        assertEquals("User registered", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(userId, userDto)).thenReturn(userDto);
        when(localizationService.getMessage("users.updated")).thenReturn("User updated");

        RemoteResponse response = userController.updateUser(userId, userDto);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("User updated", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testGetUser() {
        when(userService.getUserById(userId)).thenReturn(userDto);
        when(localizationService.getMessage("user.obtained")).thenReturn("User obtained");

        RemoteResponse response = userController.getUser(userId);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("User obtained", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testChangeUserAccountStatus() {
        when(userService.changeAccountStatus(userId, true)).thenReturn(userDto);
        when(localizationService.getMessage("user.status.changed")).thenReturn("User account status changed");

        RemoteResponse response = userController.changeUserAccountStatus(userId, true);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("User account status changed", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testChangeRole() {
        when(userService.changeRole(userId, Role.ADMIN)).thenReturn(userDto);
        when(localizationService.getMessage("user.role.changed")).thenReturn("User role changed");

        RemoteResponse response = userController.changeRole(userId, Role.ADMIN);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("User role changed", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(userDto, response.getResults().get(0));
    }

    @Test
    void testBalanceTopUp() {
        RequestAmount requestAmount = new RequestAmount(100.00);
        when(localizationService.getMessage("user.balance.changed")).thenReturn("User balance changed");

        RemoteResponse response = userController.balanceTopUp(userId, requestAmount);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("User balance changed", response.getStatusMessage());
    }
}
