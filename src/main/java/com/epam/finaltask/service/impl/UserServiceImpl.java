package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.TravelAgencyUserDetails;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public UserDTO register(UserDTO userDTO) {
        setDefaultFields(userDTO);
        User newUser = userMapper.toUser(userDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserException(id, messageSource));
        setUpdateFields(userDTO, userToUpdate);
        User updatedUser = userRepository.save(userToUpdate);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO changeRole(UUID id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id, messageSource));
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO changeAccountStatus(UUID id, boolean accountStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id, messageSource));
        user.setAccountStatus(accountStatus);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id, messageSource));
        return userMapper.toUserDTO(user);
    }

    @Override
    public void createAdmin(String adminName, String adminPassword) {
        if (!userRepository.existsByUsername(adminName)) {
            User admin = User.builder()
                    .username(adminName)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .accountStatus(true)
                    .build();
            userRepository.save(admin);
        }
    }

    @Override
    public Page<UserDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserDTO);
    }

    @Override
    public void balanceTopUp(UUID id, Double amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(id, messageSource));
        user.setBalance(user.getBalance() + amount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new TravelAgencyUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(username, messageSource)));
    }

    private void setDefaultFields(UserDTO userDTO) {
        userDTO.setRole(Role.USER.name());
        userDTO.setAccountStatus(true);
        userDTO.setBalance(0.0);
    }


    private static void setUpdateFields(UserDTO userDTO, User userToUpdate) {
        userToUpdate.setUsername(userDTO.getUsername());
        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        userToUpdate.setBalance(userDTO.getBalance());
    }
}

