package com.epam.finaltask.controller;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserController {
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user in the system with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid password or missing data")
    })
    ResponseEntity<RemoteResponse> registerUser(UserDTO userDTO);

    @Operation(
            summary = "Update user data",
            description = "Updates the details of an existing user identified by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input or missing data")
    })
    ResponseEntity<RemoteResponse> updateUser(UUID id, UserDTO userDTO);

    @Operation(
            summary = "Retrieve user by id",
            description = "Fetches user details using the provided id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<RemoteResponse> getUser(UUID id);

    @Operation(
            summary = "Change user account status",
            description = "Modifies the account status of a user identified by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input or status")
    })
    ResponseEntity<RemoteResponse> changeUserAccountStatus(UUID id, boolean accountStatus);

    @Operation(
            summary = "Change user role",
            description = "Modifies the role of a user identified by od."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input or role")
    })
    ResponseEntity<RemoteResponse> changeRole(UUID id, String role);


}

