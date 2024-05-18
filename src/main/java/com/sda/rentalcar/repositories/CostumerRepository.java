package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer,Long> {
}
