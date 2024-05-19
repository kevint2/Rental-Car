package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Long> {
}
