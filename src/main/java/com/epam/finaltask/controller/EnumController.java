package com.epam.finaltask.controller;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Enum Controller", description = "API for retrieving enum values used in filters, object creation, and other functionalities.")
@RestController
@RequestMapping("/enums")
public interface EnumController {

    @Operation(
            summary = "Retrieve the list of hotel types",
            description = "Returns all available hotel types as a list of enum values. Used for filters or object creation on the frontend."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel type successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    List<HotelType> getHotelType();

    @Operation(
            summary = "Retrieve the list of tour types",
            description = "Returns all available tour types as a list of enum values. Useful for populating dropdowns or filters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tour type successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    List<TourType> getTourType();

    @Operation(
            summary = "Retrieve the list of transfer types",
            description = "Returns all available transfer types as a list of enum values, used in filtering and object creation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer type successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    List<TransferType> getTransferType();

    @Operation(
            summary = "Retrieve the list of voucher statuses",
            description = "Provides all possible voucher statuses as a list of enum values, useful for frontend operations such as filtering and display."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher status type successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error")
    })
    List<VoucherStatus> getVoucherStatus();
}
