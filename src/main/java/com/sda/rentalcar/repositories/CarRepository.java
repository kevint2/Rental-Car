package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.static_data.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> , JpaSpecificationExecutor<Car> {

    List<Car>findAllByBrand(String brand);
    Optional<Car> findById(Long id);
    List<Car> findAllByBranchIdAndStatus(Long branchId , Status status);

    @Query("select c from Car c inner join Reservation r on r.car = c where r.dateFrom = :localDate")
    List<Car> findAllByDate(LocalDate localDate);
}
