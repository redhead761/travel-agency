package com.epam.finaltask.service;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.model.Role;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserDTO register(UserDTO userDTO);

    UserDTO updateUser(UUID id, UserDTO userDTO);

    UserDTO changeRole(UUID id, Role role);

    UserDTO changeAccountStatus(UUID id, boolean accountStatus);

    UserDTO getUserById(UUID id);

    void createAdmin(String adminName, String adminPassword);

    Page<UserDTO> findAll(int page, int size);

    void balanceTopUp(UUID id, Double amount);
}
