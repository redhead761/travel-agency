package com.epam.finaltask.service;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.model.Role;
import java.util.List;

import java.util.UUID;

public interface UserService {
    UserDTO register(UserDTO userDTO);

    UserDTO updateUser(UUID id, UserDTO userDTO);

    UserDTO changeRole(UUID id, Role role);

    UserDTO changeAccountStatus(UUID id, boolean accountStatus);

    UserDTO getUserById(UUID id);

    void createAdmin(String adminName, String adminPassword);

    List<UserDTO> findAll(int page, int size);
}
