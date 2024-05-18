package com.sda.rentalcar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rental_position")
@Data
public class Position {
    @Id
    private String position;
}
