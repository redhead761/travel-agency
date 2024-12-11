package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.UserController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "User", description = "Users func")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private static final String USER_REGISTERED = "User is successfully registered";
    private static final String USER_UPDATED = "User is successfully updated";
    private static final String USER_OBTAINED = "User was obtained successfully";

    @Override
    @PostMapping("/register")
    public ResponseEntity<RemoteResponse> registerUser(@Validated(OnCreate.class)
                                                       @RequestBody UserDTO userDto) {
        UserDTO createdUserDto = userService.register(userDto);

        return ResponseEntity.status(CREATED)
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_REGISTERED)
                        .results(List.of(createdUserDto))
                        .build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RemoteResponse> updateUser(@PathVariable UUID id,
                                                     @Validated @RequestBody UserDTO userDto) {
        UserDTO updatedUserDto = userService.updateUser(id, userDto);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_UPDATED)
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
                        .statusMessage(USER_OBTAINED)
                        .results(List.of(userDTO))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RemoteResponse> changeUserAccountStatus(@PathVariable UUID id,
                                                                  @RequestParam boolean accountStatus) {
        //UserDTO updatedUserDto = userService.updateUser(username, accountStatus);
        UserDTO updatedUserDto = userService.changeAccountStatus(id, accountStatus);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_UPDATED)
                        .results(List.of(updatedUserDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<RemoteResponse> changeRole(@PathVariable UUID id,
                                                     @RequestParam Role role) {
        UserDTO updatedUserDto = userService.changeRole(id, role);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_UPDATED)
                        .results(List.of(updatedUserDto))
                        .build());
    }
}


