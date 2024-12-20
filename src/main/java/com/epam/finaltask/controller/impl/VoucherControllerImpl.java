package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.VoucherController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.dto.group.OnUpdate;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
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
    public ResponseEntity<RemoteResponse> findAll(@RequestParam(required = false) TourType tourType,
                                                  @RequestParam(required = false) TransferType transferType,
                                                  @RequestParam(required = false) HotelType hotelType,
                                                  @RequestParam(required = false) UUID userId,
                                                  @RequestParam(required = false) Double minPrice,
                                                  @RequestParam(required = false) Double maxPrice,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        List<VoucherDTO> result = voucherService.findAllByFilter(tourType, transferType, hotelType,
                userId, minPrice, maxPrice, page, size);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(GETTING_ALL_VOUCHERS)
                        .results(result)
                        .build());
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<RemoteResponse> createVoucher(@Validated(OnCreate.class) @RequestBody VoucherDTO voucherDTO) {
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
    @PatchMapping("/{id}/order")
    public ResponseEntity<RemoteResponse> creatOrder(@PathVariable UUID id, @RequestParam UUID userId) {
        VoucherDTO orderedVoucherDto = voucherService.order(id, userId);
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
    @PutMapping("/{id}")
    public ResponseEntity<RemoteResponse> updateVoucher(@PathVariable UUID id,
                                                        @Validated(OnUpdate.class) @RequestBody VoucherDTO voucherDTO) {
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
    public ResponseEntity<RemoteResponse> deleteVoucher(@PathVariable UUID id) {
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
    @PatchMapping("/{id}/hot")
    public ResponseEntity<RemoteResponse> changeHotStatus(@PathVariable UUID id, @RequestParam boolean status) {
        VoucherDTO updatedVoucherDto = voucherService.changeHotStatus(id, status);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(STATUS_CHANGED)
                        .results(List.of(updatedVoucherDto))
                        .build());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RemoteResponse> changeVoucherStatus(@PathVariable UUID id, @RequestParam VoucherStatus status) {
        VoucherDTO updatedVoucherDto = voucherService.changeTourStatus(id, status);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(STATUS_CHANGED)
                        .results(List.of(updatedVoucherDto))
                        .build());
    }
}

