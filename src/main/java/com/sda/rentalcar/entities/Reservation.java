package com.sda.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    private LocalDate bookingDate;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "costumer")
    private Costumer costumer;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "car")
    private Car car;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_loan")
    private Branch branchLoan;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_department")
    private Branch branchDepartment;
    private Double amount;
}
