package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Employee;
import com.sda.rentalcar.entities.Rental;
import com.sda.rentalcar.exceptions.GenericException;

import com.sda.rentalcar.repositories.EmployeeRepository;
import com.sda.rentalcar.repositories.RentalRepository;
import com.sda.rentalcar.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RentalServicesImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Rental create(Rental rental,String ownerUsername) {

            Employee employee = employeeRepository.findByUsername(ownerUsername).get();
            rental.setEmployee(employee);
            return rentalRepository.save(rental);

    }
    @Override
    public Rental findById(Long id){
        return rentalRepository.findById(id).orElseThrow(()-> GenericException.notFound(id));
    }

}
