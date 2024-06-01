package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Car;

import java.util.List;

public interface CarService {
    Car create(Car car , Long id);

    Car updateStatusToUnavailable(Long id);

    void delete(Long id);

    List<Car> findAllByBrand(String model);

    Car findById(Long id);

    List<Car> getAllCarAvailable(Long branchId);
}
