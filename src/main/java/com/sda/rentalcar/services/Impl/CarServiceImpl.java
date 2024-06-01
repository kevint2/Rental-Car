package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.BranchRepository;
import com.sda.rentalcar.repositories.CarRepository;
import com.sda.rentalcar.services.CarService;
import com.sda.rentalcar.static_data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Car create(Car car, Long branchId) {
        if (car.getId() == null && branchRepository.findById(branchId).isPresent()) {
            Branch branch = branchRepository.findById(branchId).get();
            car.setBranch(branch);
            car.setStatus(Status.AVAILABLE);
            return carRepository.save(car);
        } else {
            throw GenericException.idISNotnull();
        }
    }

    @Override
    public Car updateStatusToUnavailable(Long carId) {
        if (carRepository.findById(carId).isPresent()) {
            Car car = findById(carId);
            car.setStatus(Status.UNAVAILABLE);
            return carRepository.save(car);
        } else {
            throw GenericException.notFound(carId);
        }
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findAllByBrand(String brand) {
        return  carRepository.findAllByBrand(brand);
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> GenericException.notFound(id));
    }

    @Override
    public List<Car> getAllCarAvailable(Long branchId) {
        if (branchRepository.findById(branchId).isPresent()) {
            return carRepository.findAllByBranchIdAndStatus(branchId, Status.AVAILABLE);
        } else {
            throw GenericException.notFound(branchId);
        }
    }
}
