package com.epam.finaltask.controller;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;

import java.util.List;

public interface EnumController {
    List<HotelType> getHotelType();

    List<TourType> getTourType();

    List<TransferType> getTransferType();

    List<VoucherStatus> getVoucherStatus();
}
