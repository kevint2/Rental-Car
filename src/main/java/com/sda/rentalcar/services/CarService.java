package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.static_data.Status;

import java.util.List;

public interface CarService {
    Car create(Car car , Long id);

    Car update(Long carId, Long mileage);

    Car updateStatus(Long id , Status status);

    void delete(Long id);

    List<Car> findAllByBrand(String model);

    Car findById(Long id);

    List<Car> getAllCarAvailable(Long branchId);

    List<Car>findAll();
}
