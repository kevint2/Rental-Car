package com.sda.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "employee_firstname")
    private String firstname;
    @Column(name = "employee_lastname")
    private String lastname;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean active;
    @ManyToOne()
    @JoinColumn(name = "employee_position")
    private Position position;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "branch")
    private Branch branch;




}
