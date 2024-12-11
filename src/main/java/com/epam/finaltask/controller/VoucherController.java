package com.epam.finaltask.controller;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
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
    ResponseEntity<RemoteResponse> findAll(
            TourType tourType, TransferType transferType, HotelType hotelType,
            UUID userId, Double minPrice, Double maxPrice,
            int page, int size);

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
    ResponseEntity<RemoteResponse> creatOrder(UUID orderId, UUID userId);

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
            summary = "Change voucher hot status",
            description = "Update the hot status of a specific voucher."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher hot status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> changeHotStatus(UUID id, boolean status);

    @Operation(
            summary = "Change voucher status",
            description = "Update the status of a specific voucher."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error, e.g., invalid input")
    })
    ResponseEntity<RemoteResponse> changeVoucherStatus(UUID id, VoucherStatus status);


}

