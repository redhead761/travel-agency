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

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Title is required field")
    @Length(max = 255, message = "Title must be between 3 and 45 characters")
    private String title;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Description is required field")
    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Price is required field")
    @Positive
    private Double price;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Tour type is required field")
    @ValidEnum(value = TourType.class, message = "Invalid value ${validatedValue} for parameter tourType. Expected type: TourType.")
    private String tourType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Transfer type is required field")
    @ValidEnum(value = TransferType.class, message = "Invalid value ${validatedValue} for parameter transferType. Expected type: TransferType.")
    private String transferType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Hotel type is required field")
    @ValidEnum(value = HotelType.class, message = "Invalid value ${validatedValue} for parameter hotelType. Expected type: HotelType.")
    private String hotelType;

    @NotBlank(groups = {OnChange.class, OnUpdate.class}, message = "Status is required field")
    @ValidEnum(groups = {OnChange.class, OnUpdate.class}, value = VoucherStatus.class,
            message = "Invalid value ${validatedValue} for parameter status. Expected type: VoucherStatus.")
    private String status;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Arrival date is required field")
    @FutureOrPresent
    private LocalDate arrivalDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Eviction date is required field")
    @FutureOrPresent
    private LocalDate evictionDate;

    private UUID userId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Hot is required field")
    private String hot;
}
