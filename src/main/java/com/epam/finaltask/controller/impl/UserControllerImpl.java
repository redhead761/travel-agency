package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.UserController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RemoteResponse> registerUser(@Validated @RequestBody UserDTO userDto) {
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
    @PatchMapping("/{username}")
    public ResponseEntity<RemoteResponse> updateUser(@PathVariable String username,
                                                     @Validated @RequestBody UserDTO userDto) {
        UserDTO updatedUserDto = userService.updateUser(username, userDto);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_UPDATED)
                        .results(List.of(updatedUserDto))
                        .build());
    }

    @Override
    @GetMapping("/{username}")
    public ResponseEntity<RemoteResponse> getUserByUsername(@PathVariable("username") String username) {
        UserDTO userDTO = userService.getUserByUsername(username);

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
    @PatchMapping("/change/{username}")
    public ResponseEntity<RemoteResponse> changeUserAccountStatus(@PathVariable String username,
                                                                  @Validated @RequestBody UserDTO userDto) {
        UserDTO updatedUserDto = userService.updateUser(username, userDto);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(USER_UPDATED)
                        .results(List.of(updatedUserDto))
                        .build());
    }
}


