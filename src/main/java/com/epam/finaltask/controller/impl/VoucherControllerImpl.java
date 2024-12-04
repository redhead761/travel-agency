package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.VoucherController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.service.VoucherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.finaltask.exception.StatusCodes.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Voucher", description = "Voucher func")
public class VoucherControllerImpl implements VoucherController {

    private final VoucherService voucherService;
    private static final String GETTING_ALL_VOUCHERS = "The request for getting all existed vouchers is successful";
    private static final String SUCCESSFULLY_CREATED = "Voucher is successfully created";
    private static final String SUCCESSFULLY_ORDERED = "Voucher is  successfully ordered";
    private static final String SUCCESSFULLY_UPDATED = "Voucher is successfully updated";
    private static final String SUCCESSFULLY_DELETED = "Voucher with Id %s has been deleted";
    private static final String STATUS_CHANGED = "Voucher status is successfully changed";

    @Override
    @GetMapping
    public ResponseEntity<RemoteResponse> findAll() {
        List<VoucherDTO> result = voucherService.findAll();

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<RemoteResponse> findAllByUserId(@PathVariable String userId) {
        List<VoucherDTO> result = voucherService.findAllByUserId(userId);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @GetMapping("/tour/{tourType}")
    public ResponseEntity<RemoteResponse> findAllByTourType(@PathVariable String tourType) {
        List<VoucherDTO> result = voucherService.findAllByTourType(tourType);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }


    @Override
    @GetMapping("/transfer/{transferType}")
    public ResponseEntity<RemoteResponse> findAllByTransferType(@PathVariable String transferType) {
        List<VoucherDTO> result = voucherService.findAllByTransferType(transferType);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @GetMapping("/hotel/{hotelType}")
    public ResponseEntity<RemoteResponse> findAllByHotelType(@PathVariable String hotelType) {
        List<VoucherDTO> result = voucherService.findAllByHotelType(hotelType);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @GetMapping("/price/{price}")
    public ResponseEntity<RemoteResponse> findAllByPrice(@PathVariable String price) {
        List<VoucherDTO> result = voucherService.findAllByPrice(price);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<RemoteResponse> createVoucher(@Validated @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucherDto = voucherService.create(voucherDTO);

        return ResponseEntity.status(CREATED).
                body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(SUCCESSFULLY_CREATED)
                        .results(List.of(createdVoucherDto))
                        .build());
    }

    @Override
    @PostMapping("/{orderId}/{userId}")
    public ResponseEntity<RemoteResponse> orderVoucher(@PathVariable String orderId,
                                                       @PathVariable String userId) {
        VoucherDTO orderedVoucherDto = voucherService.order(orderId, userId);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(SUCCESSFULLY_ORDERED)
                        .results(List.of(orderedVoucherDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/change/{id}")
    public ResponseEntity<RemoteResponse> updateVoucher(@PathVariable String id,
                                                        @Validated @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO updatedVoucherDto = voucherService.update(id, voucherDTO);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(SUCCESSFULLY_UPDATED)
                        .results(List.of(updatedVoucherDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RemoteResponse> deleteById(@PathVariable String id) {
        voucherService.delete(id);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(String.format(SUCCESSFULLY_DELETED, id))
                        .results(null)
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PatchMapping("/{id}")
    public ResponseEntity<RemoteResponse> changeVoucherStatus(@PathVariable String id,
                                                              @Validated @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO updatedVoucherDto = voucherService.changeHotStatus(id, voucherDTO);

        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(STATUS_CHANGED)
                        .results(List.of(updatedVoucherDto))
                        .build());
    }
}

