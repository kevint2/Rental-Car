package com.sda.rentalcar.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "refunds")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "refund_id")
    private  Long refundId;
    @Column(name = "refund_date_of_return")
    private LocalDate refundDateOfReturn;
    @ManyToOne
    @JoinColumn (name = "refund_employee")
    private  Employee employee;
    @OneToOne
     @JoinColumn(name = "refund_reservation")
     private Reservation reservation;
    @Column(name = "refund_surcharge")
    private Double surcharge;
    @Column(name = "refund_comments")
    private  String comments;
}
