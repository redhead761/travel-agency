package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.UserException;
import com.epam.finaltask.exception.VoucherException;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceImplTest {

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VoucherMapper voucherMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        VoucherDTO voucherDTO = new VoucherDTO();
        Voucher voucher = new Voucher();
        Voucher savedVoucher = new Voucher();
        VoucherDTO savedVoucherDTO = new VoucherDTO();

        when(voucherMapper.toVoucher(voucherDTO)).thenReturn(voucher);
        when(voucherRepository.save(voucher)).thenReturn(savedVoucher);
        when(voucherMapper.toVoucherDTO(savedVoucher)).thenReturn(savedVoucherDTO);

        VoucherDTO result = voucherService.create(voucherDTO);

        assertNotNull(result);
        verify(voucherMapper).toVoucher(voucherDTO);
        verify(voucherRepository).save(voucher);
        verify(voucherMapper).toVoucherDTO(savedVoucher);
    }

    @Test
    void order() {
        UUID voucherId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Voucher voucher = new Voucher();
        voucher.setStatus(VoucherStatus.REGISTERED);
        voucher.setPrice(100.0);
        User user = new User();
        user.setBalance(150.0);

        Voucher updatedVoucher = new Voucher();
        VoucherDTO updatedVoucherDTO = new VoucherDTO();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(voucherRepository.save(voucher)).thenReturn(updatedVoucher);
        when(voucherMapper.toVoucherDTO(updatedVoucher)).thenReturn(updatedVoucherDTO);

        VoucherDTO result = voucherService.order(voucherId, userId);

        assertNotNull(result);
        assertEquals(VoucherStatus.PAID, voucher.getStatus());
        assertEquals(50.0, user.getBalance());
        verify(voucherRepository).findById(voucherId);
        verify(userRepository).findById(userId);
        verify(voucherRepository).save(voucher);
        verify(voucherMapper).toVoucherDTO(updatedVoucher);
    }

    @Test
    void orderNotFound() {
        UUID voucherId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        assertThrows(VoucherException.class, () -> voucherService.order(voucherId, userId));
        verify(voucherRepository).findById(voucherId);
    }

    @Test
    void orderUserNotFound() {
        UUID voucherId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Voucher voucher = new Voucher();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> voucherService.order(voucherId, userId));
        verify(voucherRepository).findById(voucherId);
        verify(userRepository).findById(userId);
    }

    @Test
    void update() {
        UUID voucherId = UUID.randomUUID();
        VoucherDTO voucherDTO = new VoucherDTO();
        Voucher existingVoucher = new Voucher();
        Voucher newVoucher = new Voucher();
        Voucher updatedVoucher = new Voucher();
        VoucherDTO updatedVoucherDTO = new VoucherDTO();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(existingVoucher));
        when(voucherMapper.toVoucher(voucherDTO)).thenReturn(newVoucher);
        when(voucherRepository.save(existingVoucher)).thenReturn(updatedVoucher);
        when(voucherMapper.toVoucherDTO(updatedVoucher)).thenReturn(updatedVoucherDTO);

        VoucherDTO result = voucherService.update(voucherId, voucherDTO);

        assertNotNull(result);
        verify(voucherRepository).findById(voucherId);
        verify(voucherRepository).save(existingVoucher);
        verify(voucherMapper).toVoucherDTO(updatedVoucher);
    }

    @Test
    void delete() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));

        voucherService.delete(voucherId);

        verify(voucherRepository).findById(voucherId);
        verify(voucherRepository).delete(voucher);
    }

    @Test
    void deleteVoucherNotFound() {
        UUID voucherId = UUID.randomUUID();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        assertThrows(VoucherException.class, () -> voucherService.delete(voucherId));
        verify(voucherRepository).findById(voucherId);
    }
}
