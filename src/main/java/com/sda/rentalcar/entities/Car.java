package com.sda.rentalcar.entities;

import com.sda.rentalcar.static_data.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")

    private Long Id;
    private String brand;
    private String model;
    private String BodyType;
    private Integer year;
    private String color;
    private Long mileage;
    private Status status;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "branch")
    private Branch branch;

}
