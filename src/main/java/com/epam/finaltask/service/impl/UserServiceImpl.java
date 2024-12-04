package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.exception.EntityAlreadyExistsException;
import com.epam.finaltask.exception.EntityNotFoundException;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.epam.finaltask.exception.StatusCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserDTO userDTO) {
        isUniqueUsername(userDTO.getUsername());

        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountStatus(true);
        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(String username, UserDTO userDTO) {
        User userForUpdate = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "User not found"));

        User newUser = userMapper.toUser(userDTO);

        userForUpdate.setUsername(newUser.getUsername());
        userForUpdate.setRole(newUser.getRole());
        userForUpdate.setAccountStatus(newUser.isAccountStatus());
        userForUpdate.setBalance(newUser.getBalance());

        return userMapper.toUserDTO(userRepository.save(userForUpdate));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.name(), "User not found"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO changeAccountStatus(UserDTO userDTO) {
        User user = userRepository.findById(UUID.fromString(userDTO.getId()))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.name(), "User not found"));

        User newUser = userMapper.toUser(userDTO);

        user.setAccountStatus(newUser.isAccountStatus());

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.name(), "User not found"));
        return userMapper.toUserDTO(user);
    }

    private void isUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new EntityAlreadyExistsException(DUPLICATE_USERNAME.name(), "This username is already exist");
        }
    }
}

