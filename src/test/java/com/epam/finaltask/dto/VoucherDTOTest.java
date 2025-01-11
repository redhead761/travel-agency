package com.epam.finaltask.dto;

import com.epam.finaltask.dto.group.OnUpdate;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVoucherDTOValid() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Voucher Title")
                .description("Voucher Description")
                .price(100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status(VoucherStatus.REGISTERED.name())
                .arrivalDate(LocalDate.now().plusDays(1))
                .evictionDate(LocalDate.now().plusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        Set<ConstraintViolation<VoucherDTO>> violations = validator.validate(voucherDTO);
        assertTrue(violations.isEmpty(), "DTO should be valid");
    }

    @Test
    public void testVoucherDTOInvalidTitle() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("")
                .description("Voucher Description")
                .price(100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status(VoucherStatus.REGISTERED.name())
                .arrivalDate(LocalDate.now().plusDays(1))
                .evictionDate(LocalDate.now().plusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        Set<ConstraintViolation<VoucherDTO>> violations = validator.validate(voucherDTO);
        assertFalse(violations.isEmpty(), "Title should not be blank");
    }

    @Test
    public void testVoucherDTOInvalidPrice() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Voucher Title")
                .description("Voucher Description")
                .price(-100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status(VoucherStatus.REGISTERED.name())
                .arrivalDate(LocalDate.now().plusDays(1))
                .evictionDate(LocalDate.now().plusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        Set<ConstraintViolation<VoucherDTO>> violations = validator.validate(voucherDTO);
        assertFalse(violations.isEmpty(), "Price should be positive");
    }

    @Test
    public void testVoucherDTOInvalidDates() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Voucher Title")
                .description("Voucher Description")
                .price(100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status(VoucherStatus.REGISTERED.name())
                .arrivalDate(LocalDate.now().minusDays(1))
                .evictionDate(LocalDate.now().minusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        Set<ConstraintViolation<VoucherDTO>> violations = validator.validate(voucherDTO);
        assertFalse(violations.isEmpty(), "Arrival and eviction dates should be in the future or present");
    }

    @Test
    public void testVoucherDTOStatusNotBlankOnUpdate() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Voucher Title")
                .description("Voucher Description")
                .price(100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status("")
                .arrivalDate(LocalDate.now().plusDays(1))
                .evictionDate(LocalDate.now().plusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        Set<ConstraintViolation<VoucherDTO>> violations = validator.validate(voucherDTO, OnUpdate.class);
        assertFalse(violations.isEmpty(), "Status should not be blank when updating");
    }

    @Test
    public void testVoucherDTOToString() {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Voucher Title")
                .description("Voucher Description")
                .price(100.0)
                .tourType(TourType.ECO.name())
                .transferType(TransferType.PLANE.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .status(VoucherStatus.REGISTERED.name())
                .arrivalDate(LocalDate.now().plusDays(1))
                .evictionDate(LocalDate.now().plusDays(2))
                .userId(UUID.randomUUID())
                .hot("yes")
                .build();

        assertTrue(voucherDTO.toString().contains("VoucherDTO"));
    }
}
