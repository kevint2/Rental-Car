package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "costumer")
    private Costumer costumer;
    @OneToOne
    @JoinColumn(name = "car")
    private Car car;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @ManyToOne
    @JoinColumn(name = "branch_loan")
    private Branch branchLoan;
    @ManyToOne
    @JoinColumn(name = "branch_department")
    private Branch branchDepartment;
    private Long amount;
}
