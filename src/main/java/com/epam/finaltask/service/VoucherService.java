package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.EntityAlreadyExistsException;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherDTO create(VoucherDTO voucherDTO);

    VoucherDTO order(UUID id, UUID userId) throws EntityAlreadyExistsException;

    VoucherDTO update(UUID id, VoucherDTO voucherDTO);

    void delete(UUID id);

    VoucherDTO changeHotStatus(UUID id, boolean hotStatus);

    VoucherDTO changeTourStatus(UUID id, VoucherStatus status);

    List<VoucherDTO> findAllByFilter(
            TourType tourType, TransferType transferType, HotelType hotelType, UUID userId,
            Double minPrice, Double maxPrice, int page, int size);
}
