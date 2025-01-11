package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        User user = new User();
        user.setPassword("password");

        User savedUser = new User();
        UserDTO savedUserDTO = new UserDTO();

        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toUserDTO(savedUser)).thenReturn(savedUserDTO);

        UserDTO result = userService.register(userDTO);

        assertNotNull(result);
        verify(userMapper).toUser(userDTO);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
        verify(userMapper).toUserDTO(savedUser);
    }

    @Test
    void updateUser() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updatedUser");
        User existingUser = new User();
        User updatedUser = new User();
        UserDTO updatedUserDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserDTO(updatedUser)).thenReturn(updatedUserDTO);

        UserDTO result = userService.updateUser(userId, userDTO);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).toUserDTO(updatedUser);
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.updateUser(userId, userDTO));
        verify(userRepository).findById(userId);
    }

    @Test
    void changeRole() {
        UUID userId = UUID.randomUUID();
        Role newRole = Role.ADMIN;
        User existingUser = new User();
        User updatedUser = new User();
        UserDTO updatedUserDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserDTO(updatedUser)).thenReturn(updatedUserDTO);

        UserDTO result = userService.changeRole(userId, newRole);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).toUserDTO(updatedUser);
    }

    @Test
    void changeAccountStatus() {
        UUID userId = UUID.randomUUID();
        boolean newStatus = false;
        User existingUser = new User();
        User updatedUser = new User();
        UserDTO updatedUserDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserDTO(updatedUser)).thenReturn(updatedUserDTO);

        UserDTO result = userService.changeAccountStatus(userId, newStatus);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).toUserDTO(updatedUser);
    }

    @Test
    void getUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(userMapper).toUserDTO(user);
    }

    @Test
    void getUserByIdNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void createAdmin() {
        String adminName = "admin";
        String adminPassword = "adminPass";

        when(userRepository.doesNotExistByUsername(adminName)).thenReturn(true);
        when(passwordEncoder.encode(adminPassword)).thenReturn("encodedAdminPass");

        userService.createAdmin(adminName, adminPassword);

        verify(userRepository).doesNotExistByUsername(adminName);
        verify(passwordEncoder).encode(adminPassword);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createAdminExists() {
        String adminName = "admin";
        String adminPassword = "adminPass";

        when(userRepository.doesNotExistByUsername(adminName)).thenReturn(false);

        userService.createAdmin(adminName, adminPassword);

        verify(userRepository).doesNotExistByUsername(adminName);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void balanceTopUp() {
        UUID userId = UUID.randomUUID();
        double amount = 100.0;
        User user = new User();
        user.setBalance(50.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.balanceTopUp(userId, amount);

        assertEquals(150.0, user.getBalance());
        verify(userRepository).findById(userId);
    }

    @Test
    void findAll() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        User user = new User();
        UserDTO userDTO = new UserDTO();
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        Page<UserDTO> result = userService.findAll(page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository).findAll(pageable);
    }
}
