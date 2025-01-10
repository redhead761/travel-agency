package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.VoucherController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.group.OnUpdate;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import com.epam.finaltask.service.LocalizationService;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final LocalizationService localizationService;

    @Override
    @GetMapping
    public RemoteResponse findAll(@RequestParam(required = false) TourType tourType,
                                  @RequestParam(required = false) TransferType transferType,
                                  @RequestParam(required = false) HotelType hotelType,
                                  @RequestParam(required = false) UUID userId,
                                  @RequestParam(required = false) Double minPrice,
                                  @RequestParam(required = false) Double maxPrice,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "8") int size) {
        Page<VoucherDTO> result = voucherService.findAllByFilter(tourType, transferType, hotelType,
                userId, minPrice, maxPrice, page, size);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.get.all"))
                .results(result.getContent())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    @GetMapping({"/{id}"})
    public RemoteResponse getVoucher(@PathVariable UUID id) {
        VoucherDTO result = voucherService.getById(id);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.get"))
                .results(List.of(result))
                .build();
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public RemoteResponse createVoucher(@Validated @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucherDto = voucherService.create(voucherDTO);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.created"))
                .results(List.of(createdVoucherDto))
                .build();
    }

    @Override
    @PatchMapping("/{id}/order")
    public RemoteResponse creatOrder(@PathVariable UUID id, @RequestParam UUID userId) {
        VoucherDTO orderedVoucherDto = voucherService.order(id, userId);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.ordered"))
                .results(List.of(orderedVoucherDto))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public RemoteResponse updateVoucher(@PathVariable UUID id,
                                        @Validated(OnUpdate.class) @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO updatedVoucherDto = voucherService.update(id, voucherDTO);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.updated"))
                .results(List.of(updatedVoucherDto))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public RemoteResponse deleteVoucher(@PathVariable UUID id) {
        voucherService.delete(id);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.deleted"))
                .results(null)
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PatchMapping("/{id}/hot")
    public RemoteResponse changeHotStatus(@PathVariable UUID id, @RequestParam boolean status) {
        VoucherDTO updatedVoucherDto = voucherService.changeHotStatus(id, status);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("hot.status.changed"))
                .results(List.of(updatedVoucherDto))
                .build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PatchMapping("/{id}/status")
    public RemoteResponse changeVoucherStatus(@PathVariable UUID id, @RequestParam VoucherStatus status) {
        VoucherDTO updatedVoucherDto = voucherService.changeTourStatus(id, status);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("voucher.status.changed"))
                .results(List.of(updatedVoucherDto))
                .build();
    }
}
