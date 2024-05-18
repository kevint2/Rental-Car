package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
