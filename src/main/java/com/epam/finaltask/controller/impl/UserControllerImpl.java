package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.UserController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    private final MessageSource messageSource;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public RemoteResponse findAll(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> result = userService.findAll(page, size);
        return
                RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("users.obtained"))
                        .results(result.getContent())
                        .totalPages(result.getTotalPages())
                        .build();
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<RemoteResponse> registerUser(@Validated(OnCreate.class) @RequestBody UserDTO userDto) {
        UserDTO createdUserDto = userService.register(userDto);
        return ResponseEntity.status(CREATED)
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("users.registered"))
                        .results(List.of(createdUserDto))
                        .build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RemoteResponse> updateUser(@PathVariable UUID id, @Validated @RequestBody UserDTO userDto) {
        UserDTO updatedUserDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("users.updated"))
                        .results(List.of(updatedUserDto))
                        .build());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RemoteResponse> getUser(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("user.obtained"))
                        .results(List.of(userDTO))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RemoteResponse> changeUserAccountStatus(@PathVariable UUID id,
                                                                  @RequestParam boolean accountStatus) {
        UserDTO updatedUserDto = userService.changeAccountStatus(id, accountStatus);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("user.status.changed"))
                        .results(List.of(updatedUserDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<RemoteResponse> changeRole(@PathVariable UUID id, @RequestParam Role role) {
        UserDTO updatedUserDto = userService.changeRole(id, role);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("user.role.changed"))
                        .results(List.of(updatedUserDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PatchMapping("/{id}/top_up")
    public ResponseEntity<RemoteResponse> balanceTopUp(@PathVariable UUID id, @RequestParam Double amount) {
        userService.balanceTopUp(id, amount);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusMessage(OK.name())
                        .statusMessage(getLocalizedMessage("user.balance.changed"))
                        .build());
    }

    private String getLocalizedMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}


