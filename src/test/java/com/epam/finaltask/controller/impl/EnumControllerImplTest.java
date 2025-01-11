package com.epam.finaltask.controller.impl;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class EnumControllerImplTest {

    private EnumControllerImpl enumController;

    @BeforeEach
    void setUp() {
        enumController = new EnumControllerImpl();
    }

    @Test
    void testGetHotelType() {
        List<HotelType> hotelTypes = enumController.getHotelType();
        assertNotNull(hotelTypes);
        assertFalse(hotelTypes.isEmpty());
    }

    @Test
    void testGetTourType() {
        List<TourType> tourTypes = enumController.getTourType();
        assertNotNull(tourTypes);
        assertFalse(tourTypes.isEmpty());
    }

    @Test
    void testGetTransferType() {
        List<TransferType> transferTypes = enumController.getTransferType();
        assertNotNull(transferTypes);
        assertFalse(transferTypes.isEmpty());
    }

    @Test
    void testGetVoucherStatus() {
        List<VoucherStatus> voucherStatuses = enumController.getVoucherStatus();
        assertNotNull(voucherStatuses);
        assertFalse(voucherStatuses.isEmpty());
    }
}
