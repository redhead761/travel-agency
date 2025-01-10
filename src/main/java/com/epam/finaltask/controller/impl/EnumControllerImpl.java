package com.epam.finaltask.controller.impl;

import com.epam.finaltask.controller.EnumController;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enums")
public class EnumControllerImpl implements EnumController {
    @Override
    @GetMapping("/hotel")
    public List<HotelType> getHotelType() {
        return Arrays.stream(HotelType.values()).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/tour")
    public List<TourType> getTourType() {
        return Arrays.stream(TourType.values()).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/transfer")
    public List<TransferType> getTransferType() {
        return Arrays.stream(TransferType.values()).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/status")
    public List<VoucherStatus> getVoucherStatus() {
        return Arrays.stream(VoucherStatus.values()).collect(Collectors.toList());
    }
}
