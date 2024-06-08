package com.sda.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")

    private Long id;
    private String address;
    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private List<Employee>employees;
    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<Car>cars;
    @ManyToOne
    @JoinColumn(name = "rental")
    private Rental rental;
    private Boolean active;
}
