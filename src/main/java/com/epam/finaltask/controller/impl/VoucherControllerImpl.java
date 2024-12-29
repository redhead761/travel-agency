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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
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
    private final MessageSource messageSource;

    @Override
    @GetMapping
    public ResponseEntity<RemoteResponse> findAll(@RequestParam(required = false) TourType tourType,
                                                  @RequestParam(required = false) TransferType transferType,
                                                  @RequestParam(required = false) HotelType hotelType,
                                                  @RequestParam(required = false) UUID userId,
                                                  @RequestParam(required = false) Double minPrice,
                                                  @RequestParam(required = false) Double maxPrice,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "8") int size) {
        Page<VoucherDTO> result = voucherService.findAllByFilter(tourType, transferType, hotelType,
                userId, minPrice, maxPrice, page, size);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("voucher.get.all"))
                        .results(result.getContent())
                        .totalPages(result.getTotalPages())
                        .build());
    }

    @Override
    @GetMapping({"/{id}"})
    public ResponseEntity<RemoteResponse> getVoucher(@PathVariable UUID id) {
        VoucherDTO result = voucherService.getById(id);
        return ResponseEntity.ok()
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(getLocalizedMessage("voucher.get"))
                        .results(List.of(result))
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
                        .statusMessage(getLocalizedMessage("voucher.created"))
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
                        .statusMessage(getLocalizedMessage("voucher.ordered"))
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
                        .statusMessage(getLocalizedMessage("voucher.updated"))
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
                        .statusMessage(getLocalizedMessage("voucher.deleted"))
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
                        .statusMessage(getLocalizedMessage("hot.status.changed"))
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
                        .statusMessage(getLocalizedMessage("voucher.status.changed"))
                        .results(List.of(updatedVoucherDto))
                        .build());
    }

    private String getLocalizedMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}

