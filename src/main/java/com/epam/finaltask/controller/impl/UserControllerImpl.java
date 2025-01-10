package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.UserController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.service.LocalizationService;
import com.epam.finaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final LocalizationService localizationService;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public RemoteResponse findAll(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> result = userService.findAll(page, size);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("users.obtained"))
                .results(result.getContent())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    @ResponseStatus(CREATED)
    @PostMapping("/register")
    public RemoteResponse registerUser(@Validated(OnCreate.class) @RequestBody UserDTO userDto) {
        UserDTO createdUserDto = userService.register(userDto);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("users.registered"))
                .results(List.of(createdUserDto))
                .build();
    }

    @Override
    @PutMapping("/{id}")
    public RemoteResponse updateUser(@PathVariable UUID id, @Validated @RequestBody UserDTO userDto) {
        UserDTO updatedUserDto = userService.updateUser(id, userDto);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("users.updated"))
                .results(List.of(updatedUserDto))
                .build();
    }

    @Override
    @GetMapping("/{id}")
    public RemoteResponse getUser(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("user.obtained"))
                .results(List.of(userDTO))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/status")
    public RemoteResponse changeUserAccountStatus(@PathVariable UUID id,
                                                  @RequestParam boolean accountStatus) {
        UserDTO updatedUserDto = userService.changeAccountStatus(id, accountStatus);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("user.status.changed"))
                .results(List.of(updatedUserDto))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/role")
    public RemoteResponse changeRole(@PathVariable UUID id, @RequestParam Role role) {
        UserDTO updatedUserDto = userService.changeRole(id, role);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("user.role.changed"))
                .results(List.of(updatedUserDto))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PatchMapping("/{id}/top_up")
    public RemoteResponse balanceTopUp(@PathVariable UUID id, @RequestParam Double amount) {
        userService.balanceTopUp(id, amount);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusMessage(OK.name())
                .statusMessage(localizationService.getMessage("user.balance.changed"))
                .build();
    }
}
