package com.sda.rentalcar.controllers;

import com.sda.rentalcar.dto.CarCreateRequest;
import com.sda.rentalcar.dto.CarMileageUpdateRequest;
import com.sda.rentalcar.dto.CarResponse;
import com.sda.rentalcar.dto.CarStatusUpdateRequest;
import com.sda.rentalcar.dto.FilterDto;
import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/create")
    public ResponseEntity<CarResponse> create(@Valid @RequestBody CarCreateRequest request) {
        Car created = carService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CarResponse.from(created));
    }

    @PutMapping("/status")
    public ResponseEntity<CarResponse> updateStatus(@RequestParam Long carId,
                                                    @Valid @RequestBody CarStatusUpdateRequest request) {
        Car updated = carService.updateStatus(carId, request.getStatus());
        return ResponseEntity.ok(CarResponse.from(updated));
    }

    @PutMapping("/mileage")
    public ResponseEntity<CarResponse> updateMileage(@RequestParam Long carId,
                                                     @Valid @RequestBody CarMileageUpdateRequest request) {
        Car updated = carService.updateMileage(carId, request.getMileage());
        return ResponseEntity.ok(CarResponse.from(updated));
    }

    @GetMapping("/findCarById")
    public ResponseEntity<CarResponse> findById(@RequestParam Long carId) {
        return ResponseEntity.ok(CarResponse.from(carService.findById(carId)));
    }

    @GetMapping("/getAllCarAvailableByBranch")
    public ResponseEntity<List<CarResponse>> getAllAvailableCar(@RequestParam Long branchId) {
        List<CarResponse> responses = carService.getAllCarAvailable(branchId)
                .stream()
                .map(CarResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CarResponse>> filterCars(@RequestBody FilterDto filterDto) {
        List<CarResponse> responses = carService.findByFilter(filterDto)
                .stream()
                .map(CarResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getAllByBrand")
    public ResponseEntity<List<CarResponse>> getAllByModel(@RequestParam String brand) {
        List<CarResponse> responses = carService.findAllByBrand(brand)
                .stream()
                .map(CarResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponse>> getAll() {
        List<CarResponse> responses = carService.findAll()
                .stream()
                .map(CarResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
