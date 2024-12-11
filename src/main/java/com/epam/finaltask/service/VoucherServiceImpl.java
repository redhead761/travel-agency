package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.exception.VoucherException;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public VoucherDTO create(VoucherDTO voucherDTO) {
        voucherDTO.setStatus(VoucherStatus.REGISTERED.name());
        Voucher newVoucher = voucherMapper.toVoucher(voucherDTO);
        Voucher createdVoucher = voucherRepository.save(newVoucher);
        return voucherMapper.toVoucherDTO(createdVoucher);
    }

    @Override
    public VoucherDTO order(UUID voucherId, UUID userId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(voucherId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(userId));

        voucher.setUser(user);
        voucher.setStatus(VoucherStatus.REGISTERED);
        Voucher orderedVoucher = voucherRepository.save(voucher);

        return voucherMapper.toVoucherDTO(orderedVoucher);
    }

    @Override
    public VoucherDTO update(UUID id, VoucherDTO voucherDTO) {
        Voucher voucherForUpdate = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherException(id));

        Voucher newVoucher = voucherMapper.toVoucher(voucherDTO);

        voucherForUpdate.setTitle(newVoucher.getTitle());
        voucherForUpdate.setDescription(newVoucher.getDescription());
        voucherForUpdate.setPrice(newVoucher.getPrice());
        voucherForUpdate.setTourType(newVoucher.getTourType());
        voucherForUpdate.setTransferType(newVoucher.getTransferType());
        voucherForUpdate.setHotelType(newVoucher.getHotelType());
        voucherForUpdate.setStatus(newVoucher.getStatus());
        voucherForUpdate.setArrivalDate(newVoucher.getArrivalDate());
        voucherForUpdate.setEvictionDate(newVoucher.getEvictionDate());
        voucherForUpdate.setUser(newVoucher.getUser());
        voucherForUpdate.setHot(newVoucher.isHot());

        Voucher updatedVoucher = voucherRepository.save(voucherForUpdate);

        return voucherMapper.toVoucherDTO(updatedVoucher);
    }

    @Override
    public void delete(UUID id) {
        voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherException(id));

        voucherRepository.deleteById(id);
    }

    @Override
    public VoucherDTO changeHotStatus(UUID id, boolean hotStatus) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherException(id));
        voucher.setHot(hotStatus);
        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public VoucherDTO changeTourStatus(UUID id, VoucherStatus status) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherException(id));

        voucher.setStatus(status);
        Voucher updatedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(updatedVoucher);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VoucherDTO> findAllByFilter(
            TourType tourType, TransferType transferType, HotelType hotelType, UUID userId,
            Double minPrice, Double maxPrice, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("hot")));

        return voucherRepository.findByFilters(
                tourType, transferType, hotelType, userId, minPrice, maxPrice, pageable
        ).getContent().stream().map(voucherMapper::toVoucherDTO).toList();
    }
}
