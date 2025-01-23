package com.epam.finaltask.dto;

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

    @NotBlank(message = "title.not.blank")
    @Length(max = 255, message = "title.length")
    private String title;

    @NotBlank(message = "description.not.blank")
    private String description;

    @NotNull(message = "price.not.blank")
    @Positive(message = "price.positive")
    private Double price;

    @NotBlank(message = "tour.type.not.blank")
    @ValidEnum(value = TourType.class, message = "tour.type.valid")
    private String tourType;

    @NotBlank(message = "transfer.type.not.blank")
    @ValidEnum(value = TransferType.class, message = "transfer.type.valid")
    private String transferType;

    @NotBlank(message = "hotel.type.not.blank")
    @ValidEnum(value = HotelType.class, message = "hotel.type.valid")
    private String hotelType;

    @NotBlank(groups = {OnUpdate.class}, message = "status.not.blank")
    @ValidEnum(groups = {OnUpdate.class}, value = VoucherStatus.class, message = "status.valid")
    private String status;

    @NotNull(message = "arrival.not.blank")
    @FutureOrPresent(message = "date.future")
    private LocalDate arrivalDate;

    @NotNull(message = "eviction.not.blank")
    @FutureOrPresent(message = "date.future")
    private LocalDate evictionDate;

    private UUID userId;

    @NotNull(message = "hot.not.blank")
    private String hot;

    private int version;
}
