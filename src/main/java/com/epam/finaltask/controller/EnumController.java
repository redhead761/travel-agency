package com.epam.finaltask.controller;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;

import java.util.List;

public interface EnumController {
    List<HotelType> getHotelType();

    List<TourType> getTourType();

    List<TransferType> getTransferType();
}
