package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.exception.VoucherException;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;
    private final VoucherMapper voucherMapper;
    private final MessageSource messageSource;
    private static final String BY_HOT = "hot";
    private static final String BY_ID = "id";

    @Override
    public VoucherDTO create(VoucherDTO voucherDTO) {
        voucherDTO.setStatus(VoucherStatus.REGISTERED.name());
        Voucher newVoucher = voucherMapper.toVoucher(voucherDTO);
        Voucher createdVoucher = voucherRepository.save(newVoucher);
        return voucherMapper.toVoucherDTO(createdVoucher);
    }

    @Override
    public VoucherDTO order(UUID voucherId, UUID userId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new VoucherException(voucherId, messageSource));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(userId, messageSource));
        Voucher orderedVoucher = addUserToVoucher(voucher, user);
        addVoucherToUser(user, orderedVoucher);
        return voucherMapper.toVoucherDTO(orderedVoucher);
    }


    @Override
    public VoucherDTO update(UUID id, VoucherDTO voucherDTO) {
        Voucher voucherForUpdate = voucherRepository.findById(id).orElseThrow(() -> new VoucherException(id, messageSource));
        Voucher newVoucher = voucherMapper.toVoucher(voucherDTO);
        setVoucherFields(voucherForUpdate, newVoucher);
        Voucher updatedVoucher = voucherRepository.save(voucherForUpdate);
        return voucherMapper.toVoucherDTO(updatedVoucher);
    }


    @Override
    public void delete(UUID id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new VoucherException(id, messageSource));
        voucherRepository.delete(voucher);
    }

    @Override
    public VoucherDTO changeHotStatus(UUID id, boolean hotStatus) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new VoucherException(id, messageSource));
        voucher.setHot(hotStatus);
        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public VoucherDTO changeTourStatus(UUID id, VoucherStatus status) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new VoucherException(id, messageSource));
        voucher.setStatus(status);
        Voucher updatedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(updatedVoucher);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VoucherDTO> findAllByFilter(TourType tourType, TransferType transferType, HotelType hotelType, UUID userId,
                                            Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc(BY_HOT), Sort.Order.asc(BY_ID)));
        Page<Voucher> vouchers = voucherRepository.findByFilters(tourType, transferType, hotelType, userId, minPrice, maxPrice, pageable);
        return vouchers.map(voucherMapper::toVoucherDTO);
    }

    @Override
    public VoucherDTO getById(UUID id) {
        return voucherMapper.toVoucherDTO(voucherRepository.findById(id).orElseThrow(() -> new VoucherException(id, messageSource)));
    }

    private static void addVoucherToUser(User user, Voucher orderedVoucher) {
        if (user.getVouchers() == null) {
            user.setVouchers(new ArrayList<>());
        }
        user.getVouchers().add(orderedVoucher);
    }

    private Voucher addUserToVoucher(Voucher voucher, User user) {
        voucher.setUser(user);
        voucher.setStatus(VoucherStatus.PAID);
        return voucherRepository.save(voucher);
    }

    private static void setVoucherFields(Voucher voucherForUpdate, Voucher newVoucher) {
        voucherForUpdate.setTitle(newVoucher.getTitle());
        voucherForUpdate.setDescription(newVoucher.getDescription());
        voucherForUpdate.setPrice(newVoucher.getPrice());
        voucherForUpdate.setTourType(newVoucher.getTourType());
        voucherForUpdate.setTransferType(newVoucher.getTransferType());
        voucherForUpdate.setHotelType(newVoucher.getHotelType());
        voucherForUpdate.setStatus(newVoucher.getStatus());
        voucherForUpdate.setArrivalDate(newVoucher.getArrivalDate());
        voucherForUpdate.setEvictionDate(newVoucher.getEvictionDate());
        voucherForUpdate.setHot(newVoucher.isHot());
    }
}
