package com.sda.rentalcar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CarMileageUpdateRequest {
    @NotNull(message = "Mileage is mandatory")
    @PositiveOrZero(message = "Mileage must not be negative")
    private Long mileage;
}
