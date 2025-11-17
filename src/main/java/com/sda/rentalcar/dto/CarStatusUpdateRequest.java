package com.sda.rentalcar.dto;

import com.sda.rentalcar.static_data.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarStatusUpdateRequest {
    @NotNull(message = "Status is mandatory")
    private Status status;
}
