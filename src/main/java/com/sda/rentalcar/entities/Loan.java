package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;
    private LocalDateTime rentalOfRental;
    @OneToOne
    private Reservation reservation;
    private String ccomment;

}
