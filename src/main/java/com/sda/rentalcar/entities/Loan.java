package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "employee")
    private Employee employee;
    private LocalDate dateOfRental;
    @OneToOne
    private Reservation reservation;
    private String comment;

}
