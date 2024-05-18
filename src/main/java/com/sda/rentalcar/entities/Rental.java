package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;
    private String name;
    private String owner;
    @OneToMany()
    @JoinColumn(name = "branches")
    private List<Branch>branches;
}
