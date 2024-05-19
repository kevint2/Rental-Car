package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {

    Optional<Car> findByModel(String model);
    Optional<Car> findById(Long id);
}
