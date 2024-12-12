package com.epam.finaltask.dto;

import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.dto.group.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDTO {
    private String id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Title is required field")
    @Length(max = 255,
            message = "Title must be between 3 and 45 characters")
    private String title;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Description is required field")
    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Price is required field")
    private BigDecimal price;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Tour type is required field")
    private String tourType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Transfer type is required field")
    private String transferType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Hotel type is required field")
    private String hotelType;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Status is required field")
    private String status;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Arrival date is required field")
    private LocalDate arrivalDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Eviction date is required field")
    private LocalDate evictionDate;

    private UUID userId;

    private String isHot;
}
