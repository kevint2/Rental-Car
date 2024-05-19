package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Car;

public interface CarService {
    Car create(Car car);

    Car update(Car car);

    void delete(Long id);

    Car findByModel(String model);
}
