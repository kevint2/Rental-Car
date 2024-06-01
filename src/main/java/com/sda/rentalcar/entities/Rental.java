package com.sda.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "rental")
    private List<Branch>branches;
    @JsonIgnore
    @OneToMany(mappedBy = "rental")
    private List<Costumer>costumers;
    @OneToOne
    @JoinColumn(name = "owner")
    private Employee employee;
}
