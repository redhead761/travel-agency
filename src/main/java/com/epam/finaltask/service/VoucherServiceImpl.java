package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.EntityNotFoundException;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.epam.finaltask.exception.StatusCodes.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public VoucherDTO create(VoucherDTO voucherDTO) {
        Voucher voucher = voucherMapper.toVoucher(voucherDTO);
        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public VoucherDTO order(UUID voucherId, UUID userId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Voucher not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "User not found"));

        voucher.setUser(user);
        voucher.setStatus(VoucherStatus.REGISTERED);

        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public VoucherDTO update(UUID id, VoucherDTO voucherDTO) {
        Voucher voucherForUpdate = voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Voucher not found"));

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

        return voucherMapper.toVoucherDTO(voucherRepository.save(voucherForUpdate));
    }

    @Override
    public void delete(UUID id) {
        voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Voucher not found"));

        voucherRepository.deleteById(id);
    }

    @Override
    public VoucherDTO changeHotStatus(UUID id, boolean hotStatus) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND.name(), "Voucher not found"));

//        Voucher newVoucher = voucherMapper.toVoucher(voucherDTO);

        voucher.setHot(hotStatus);
//        voucher.setStatus(newVoucher.getStatus());

        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public List<VoucherDTO> findAllByUserId(UUID id) {
        return voucherRepository.findAllByUserId(id).stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }

    @Override
    public List<VoucherDTO> findAllByTourType(String tourType) {
        return voucherRepository.findAllByTourType(TourType.valueOf(tourType)).stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }

    @Override
    public List<VoucherDTO> findAllByTransferType(String transferType) {
        return voucherRepository.findAllByTransferType(TransferType.valueOf(transferType)).stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }

    @Override
    public List<VoucherDTO> findAllByPrice(String price) {
        return voucherRepository.findAllByPrice(Double.valueOf(price)).stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }

    @Override
    public List<VoucherDTO> findAllByHotelType(String hotelType) {
        return voucherRepository.findAllByHotelType(HotelType.valueOf(hotelType)).stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }

    @Override
    public List<VoucherDTO> findAll() {
        return voucherRepository.findAll().stream()
                .map(voucherMapper::toVoucherDTO).toList();
    }
}
