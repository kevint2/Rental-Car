package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.dto.CarCreateRequest;
import com.sda.rentalcar.dto.FilterDto;
import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.entities.Car;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.BranchRepository;
import com.sda.rentalcar.repositories.CarRepository;
import com.sda.rentalcar.services.CarService;
import com.sda.rentalcar.static_data.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@EnableScheduling
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Car> findByFilter(FilterDto filterDto) {
        Specification<Car> carSpecification = ((root, query, criteriaBuilder) -> {
            List<Predicate> finalPredicate = new ArrayList<>();
            if (filterDto.getBrand() != null) {
                Predicate brandPredicate = criteriaBuilder.equal(root.get("brand"), filterDto.getBrand());
                finalPredicate.add(brandPredicate);
            }
            if (filterDto.getModel() != null) {
                finalPredicate.add(criteriaBuilder.equal(root.get("model"), filterDto.getModel()));
            }
            if (filterDto.getColor() != null) {
                finalPredicate.add(criteriaBuilder.equal(root.get("color"), filterDto.getColor()));
            }
            if (filterDto.getAmount() != null) {
                finalPredicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), filterDto.getAmount()));

            }
            if (filterDto.getYear() != null) {
                finalPredicate.add(criteriaBuilder.equal(root.get("year"), filterDto.getYear()));
            }
            return criteriaBuilder.and((finalPredicate.toArray(new Predicate[0])));

        });
        return carRepository.findAll(carSpecification);
    }

    @Override
    public Car create(CarCreateRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> GenericException.notFound(request.getBranchId()));

        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setBodyType(request.getBodyType());
        car.setYear(request.getYear());
        car.setColor(request.getColor());
        car.setMileage(request.getMileage());
        car.setAmount(request.getAmount());
        car.setImageUrl(request.getImageUrl());
        car.setBranch(branch);
        car.setStatus(Status.AVAILABLE);
        return carRepository.save(car);
    }

    @Override
    public Car updateMileage(Long carId, Long mileage) {
        Car car = carRepository.findById(carId).orElseThrow(() -> GenericException.notFound(carId));
        car.setMileage(mileage);
        return carRepository.save(car);
    }

    @Override
    public Car updateStatus(Long carId, Status status) {
        Car car = carRepository.findById(carId).orElseThrow(() -> GenericException.notFound(carId));
        car.setStatus(status);
        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findAllByBrand(String brand) {
        return carRepository.findAllByBrand(brand);
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

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Scheduled(cron = "0 00 01 * * ?", zone = "Europe/Rome")
    public void checkReservation() {
        LocalDate today = LocalDate.now();
        List<Car> carsToBook = carRepository.findAllReservedOnDate(today);
        carsToBook.forEach(car -> {
            if (car.getStatus() != Status.BOOKED) {
                car.setStatus(Status.BOOKED);
                carRepository.save(car);
            }
        });

        List<Car> carsToRelease = carRepository.findAllReservationsEndedBefore(today);
        carsToRelease.forEach(car -> {
            if (car.getStatus() != Status.AVAILABLE) {
                car.setStatus(Status.AVAILABLE);
                carRepository.save(car);
            }
        });
    }
}
