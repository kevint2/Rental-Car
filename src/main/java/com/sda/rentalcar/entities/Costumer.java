package com.sda.rentalcar.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "costumers")
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "costumer_id")
    private Long costumerId;
    @Column(name = "costumer_firstname")
    private String firstname;
    @Column(name = "costumer_lastname")
    private String lastname;
    @Column(name = "costumer_email")
    private String email;
    @Column(name = "costumer_address")
    private String address;

}
