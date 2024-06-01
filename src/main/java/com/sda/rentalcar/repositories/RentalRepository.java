package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Costumer;
import com.sda.rentalcar.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental,Long> {

    Optional<Rental> findById(Long id);


}
