package com.epam.finaltask.controller;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface VoucherController {
    @Operation(
            summary = "Get all vouchers",
            description = "Retrieve a list of all available vouchers."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAll();

    @Operation(
            summary = "Get vouchers by user ID",
            description = "Retrieve all vouchers associated with a specific user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAllByUserId(UUID id);

    @Operation(
            summary = "Get vouchers by tour type",
            description = "Retrieve vouchers filtered by the specified tour type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAllByTourType(String tourType);

    @Operation(
            summary = "Get vouchers by transfer type",
            description = "Retrieve vouchers filtered by the specified transfer type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAllByTransferType(String transferType);

    @Operation(
            summary = "Get vouchers by hotel type",
            description = "Retrieve vouchers filtered by the specified hotel type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAllByHotelType(String hotelType);

    @Operation(
            summary = "Get vouchers by price",
            description = "Retrieve vouchers filtered by the specified price range."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vouchers successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    ResponseEntity<RemoteResponse> findAllByPrice(String price);

    @Operation(
            summary = "Create a new voucher",
            description = "Add a new voucher to the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voucher successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> createVoucher(VoucherDTO voucherDTO);

    @Operation(
            summary = "Order a voucher",
            description = "Place an order for a specific voucher."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voucher successfully ordered"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> orderVoucher(UUID orderId, UUID userId);

    @Operation(
            summary = "Update voucher data",
            description = "Modify the details of an existing voucher."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> updateVoucher(UUID id, VoucherDTO voucherDTO);

    @Operation(
            summary = "Delete a voucher",
            description = "Remove a voucher identified by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    ResponseEntity<RemoteResponse> deleteVoucher(UUID id);

    @Operation(
            summary = "Change voucher status",
            description = "Update the status of a specific voucher."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> changeVoucherStatus(UUID id, boolean status);
}

