package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.EntityAlreadyExistsException;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherDTO create(VoucherDTO voucherDTO);

    VoucherDTO order(UUID id, UUID userId) throws EntityAlreadyExistsException;

    VoucherDTO update(UUID id, VoucherDTO voucherDTO);

    void delete(UUID id);

    VoucherDTO changeHotStatus(UUID id, boolean hotStatus);

    List<VoucherDTO> findAllByUserId(UUID userId);

    List<VoucherDTO> findAllByTourType(String tourType);

    List<VoucherDTO> findAllByTransferType(String transferType);

    List<VoucherDTO> findAllByPrice(String price);

    List<VoucherDTO> findAllByHotelType(String hotelType);

    List<VoucherDTO> findAll();
}
