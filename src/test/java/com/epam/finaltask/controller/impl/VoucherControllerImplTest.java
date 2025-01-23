package com.epam.finaltask.controller.impl;

import com.epam.finaltask.dto.HotStatusRequest;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherStatusRequest;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.model.VoucherStatus;
import com.epam.finaltask.service.LocalizationService;
import com.epam.finaltask.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VoucherControllerImplTest {

    @Mock
    private VoucherService voucherService;

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private VoucherControllerImpl voucherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Page<VoucherDTO> voucherPage = new PageImpl<>(List.of(new VoucherDTO()));
        when(voucherService.findAllByFilter(any(), any(), any(), any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(voucherPage);
        when(localizationService.getMessage("voucher.get.all")).thenReturn("Voucher list obtained");

        RemoteResponse response = voucherController.findAll(null, null, null, null, null, null, 1, 8);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher list obtained", response.getStatusMessage());
        assertEquals(1, response.getResults().size()); // Check if there is one voucher in the result
    }

    @Test
    void testGetVoucher() {
        UUID id = UUID.randomUUID();
        VoucherDTO voucherDto = new VoucherDTO();
        when(voucherService.getById(id)).thenReturn(voucherDto);
        when(localizationService.getMessage("voucher.get")).thenReturn("Voucher obtained");

        RemoteResponse response = voucherController.getVoucher(id);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher obtained", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    @Test
    void testCreateVoucher() {
        VoucherDTO voucherDto = new VoucherDTO();
        when(voucherService.create(voucherDto)).thenReturn(voucherDto);
        when(localizationService.getMessage("voucher.created")).thenReturn("Voucher created");

        RemoteResponse response = voucherController.createVoucher(voucherDto);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusCode());
        assertEquals("Voucher created", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    @Test
    void testCreatOrder() {
        UUID voucherId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        VoucherDTO orderedVoucherDto = new VoucherDTO();
        when(voucherService.order(voucherId, userId)).thenReturn(orderedVoucherDto);
        when(localizationService.getMessage("voucher.ordered")).thenReturn("Voucher ordered");

        RemoteResponse response = voucherController.creatOrder(voucherId, userId);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher ordered", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    @Test
    void testUpdateVoucher() {
        UUID id = UUID.randomUUID();
        VoucherDTO voucherDto = new VoucherDTO();
        when(voucherService.update(id, voucherDto)).thenReturn(voucherDto);
        when(localizationService.getMessage("voucher.updated")).thenReturn("Voucher updated");

        RemoteResponse response = voucherController.updateVoucher(id, voucherDto);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher updated", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    @Test
    void testDeleteVoucher() {
        UUID id = UUID.randomUUID();
        doNothing().when(voucherService).delete(id);
        when(localizationService.getMessage("voucher.deleted")).thenReturn("Voucher deleted");

        RemoteResponse response = voucherController.deleteVoucher(id);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher deleted", response.getStatusMessage());
        assertNull(response.getResults());
    }

    @Test
    void testChangeHotStatus() {
        UUID id = UUID.randomUUID();
        HotStatusRequest hotStatusRequest = new HotStatusRequest(true, 1);
        VoucherDTO updatedVoucherDto = new VoucherDTO();
        when(voucherService.changeHotStatus(id, hotStatusRequest)).thenReturn(updatedVoucherDto);
        when(localizationService.getMessage("hot.status.changed")).thenReturn("Hot status changed");

        RemoteResponse response = voucherController.changeHotStatus(id, hotStatusRequest);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Hot status changed", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    @Test
    void testChangeVoucherStatus() {
        UUID id = UUID.randomUUID();
        VoucherStatusRequest voucherStatusRequest = new VoucherStatusRequest(VoucherStatus.PAID, 1);
        VoucherDTO updatedVoucherDto = new VoucherDTO();
        when(voucherService.changeVoucherStatus(id, voucherStatusRequest)).thenReturn(updatedVoucherDto);
        when(localizationService.getMessage("voucher.status.changed")).thenReturn("Voucher status changed");

        RemoteResponse response = voucherController.changeVoucherStatus(id, voucherStatusRequest);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Voucher status changed", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }
}
