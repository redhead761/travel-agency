package com.epam.finaltask.service;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
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

        userDTO.setRole(Role.USER.name());
        userDTO.setAccountStatus(true);
        User newUser = userMapper.toUser(userDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);

        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserException(id));

        isUniqueUsername(userDTO.getUsername());

        userToUpdate.setUsername(userDTO.getUsername());
        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        userToUpdate.setBalance(userDTO.getBalance());
        User updatedUser = userRepository.save(userToUpdate);

        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO changeRole(UUID id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id));
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO changeAccountStatus(UUID id, boolean accountStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id));

        user.setAccountStatus(accountStatus);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id));
        return userMapper.toUserDTO(user);
    }

    private void isUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserException(username);
        }
    }
}

