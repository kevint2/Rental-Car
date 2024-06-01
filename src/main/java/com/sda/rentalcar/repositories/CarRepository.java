package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.static_data.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car>findAllByBrand(String brand);
    Optional<Car> findById(Long id);
    List<Car> findAllByBranchIdAndStatus(Long branchId , Status status);
}
