package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revenueId;
    @OneToOne
    private Rental rental;
    @Column(name = "revenue_amount")
    private Double income;
    public Revenue(){
        this.income = 0.0;
    }


}