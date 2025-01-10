package com.epam.finaltask.controller;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.dto.RemoteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Users authentication")
public interface AuthenticationController {
    @Operation(summary = "User authentication",
            description = "Authenticates the user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Validation Error"),
            @ApiResponse(responseCode = "423", description = "Locked, access is denied"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    RemoteResponse authenticate(Credentials credentials);

    @Operation(summary = "User logout",
            description = "Logs out the user and invalidates the JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    void logout(String authorization);
}
