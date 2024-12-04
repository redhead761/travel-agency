package com.epam.finaltask.controller;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

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
            description = "Updates the details of an existing user identified by username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input or missing data")
    })
    ResponseEntity<RemoteResponse> updateUser(String username, UserDTO userDTO);

    @Operation(
            summary = "Retrieve user by username",
            description = "Fetches user details using the provided username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<RemoteResponse> getUserByUsername(String username);

    @Operation(
            summary = "Change user account status",
            description = "Modifies the account status of a user identified by username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input or status")
    })
    ResponseEntity<RemoteResponse> changeUserAccountStatus(String username, UserDTO userDto);
}

