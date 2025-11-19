package com.sda.rentalcar.dto;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.static_data.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarResponse {
    Long id;
    String brand;
    String model;
    String bodyType;
    Integer year;
    String color;
    Long mileage;
    Status status;
    Double amount;
    String imageUrl;
    Long branchId;

    public static CarResponse from(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .bodyType(car.getBodyType())
                .year(car.getYear())
                .color(car.getColor())
                .mileage(car.getMileage())
                .status(car.getStatus())
                .amount(car.getAmount())
                .imageUrl(car.getImageUrl())
                .branchId(car.getBranch() != null ? car.getBranch().getId() : null)
                .build();
    }
}
