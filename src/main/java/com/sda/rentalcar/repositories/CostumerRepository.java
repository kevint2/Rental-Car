package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Costumer;
import com.sda.rentalcar.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostumerRepository extends JpaRepository<Costumer,Long> {
    Costumer findByEmail(String email);
    List<Costumer>findCostumerByRentalId(Long rentalId);
}
