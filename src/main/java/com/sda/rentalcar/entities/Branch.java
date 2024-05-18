package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branche_id")
    private Long id;
    private String address;
    @OneToMany(mappedBy = "branch")
    private List<Employee>employees;
    @OneToMany(mappedBy = "branch")
    private List<Car>cars;
}
