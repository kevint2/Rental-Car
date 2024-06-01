package com.sda.rentalcar.controllers;

import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping("/create")
    public Car create(@RequestBody Car car , @RequestParam Long branchId){
        return carService.create(car,branchId);
    }
    @PutMapping("/updateStatusToUnavailable")
    public Car updateStatusToUnavailable(@RequestParam Long carId){
        return carService.updateStatusToUnavailable(carId);
    }
    @GetMapping("/findCarById")
    public Car findById(@RequestParam Long carId){
      return   carService.findById(carId);
    }
    @GetMapping("/getAllCarAvailableByBranch")
    public List<Car>getAllAvailableCar(@RequestParam Long branchId){
        return carService.getAllCarAvailable(branchId);
    }
    @GetMapping("/getAllByModel")
    public List<Car>getAllByModel(@RequestParam String brand){
        return carService.findAllByBrand(brand);
    }
}
