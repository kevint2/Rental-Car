package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.CarRepository;
import com.sda.rentalcar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
@Override
    public Car create(Car car) {
        if (car.getId() == null) {
            return carRepository.save(car);
        } else {
            throw GenericException.idISNotnull();
        }
    }
@Override
    public Car update(Car car) {
        if (car.getId() != null) {
            return carRepository.save(car);
        } else {
            throw GenericException.idIsNull();
        }
    }
@Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
    @Override
    public Car findByModel(String model){
    return carRepository.findByModel(model).orElseThrow(()->GenericException.notFound(model));
    }
    public Car findById(Long id){
    return carRepository.findById(id).orElseThrow(()->GenericException.notFound(id));
    }
}
