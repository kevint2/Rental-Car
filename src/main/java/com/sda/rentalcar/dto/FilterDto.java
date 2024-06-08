package com.sda.rentalcar.dto;

import lombok.Data;

@Data
public class FilterDto {
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private Double amount;
}
