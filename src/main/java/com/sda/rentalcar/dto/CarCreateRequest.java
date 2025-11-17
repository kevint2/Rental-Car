package com.sda.rentalcar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CarCreateRequest {
    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotBlank(message = "Body type is mandatory")
    private String bodyType;

    @NotNull(message = "Year is mandatory")
    @Positive(message = "Year must be positive")
    private Integer year;

    @NotBlank(message = "Color is mandatory")
    private String color;

    @NotNull(message = "Mileage is mandatory")
    @PositiveOrZero(message = "Mileage must not be negative")
    private Long mileage;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private String imageUrl;

    @NotNull(message = "Branch id is mandatory")
    private Long branchId;
}
