package com.epam.finaltask.dto;

import com.epam.finaltask.dto.group.OnChange;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.dto.group.OnUpdate;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import com.epam.finaltask.util.validator.ValidDates;
import com.epam.finaltask.util.validator.ValidEnum;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidDates
public class VoucherDTO {
    private String id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "title.not.blank")
    @Length(max = 255, message = "title.length")
    private String title;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "description.not.blank")
    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "price.not.blank")
    @Positive(message = "price.positive")
    private Double price;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "tour.type.not.blank")
    @ValidEnum(value = TourType.class, message = "tour.type.valid")
    private String tourType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "transfer.type.not.blank")
    @ValidEnum(value = TransferType.class, message = "transfer.type.valid")
    private String transferType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "hotel.type.not.blank")
    @ValidEnum(value = HotelType.class, message = "hotel.type.valid")
    private String hotelType;

    @NotBlank(groups = {OnChange.class, OnUpdate.class}, message = "status.not.blank")
    @ValidEnum(groups = {OnChange.class, OnUpdate.class}, value = VoucherStatus.class, message = "status.valid")
    private String status;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "arrival.not.blank")
    @FutureOrPresent(message = "date.future")
    private LocalDate arrivalDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "eviction.not.blank")
    @FutureOrPresent(message = "date.future")
    private LocalDate evictionDate;

    private UUID userId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "hot.not.blank")
    private String hot;
}
