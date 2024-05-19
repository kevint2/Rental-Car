package com.sda.rentalcar.services;

import com.sda.rentalcar.dto.AuthRequest;
import com.sda.rentalcar.entities.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    Employee create(Employee employee, boolean isManager);

    ResponseEntity<?> login(AuthRequest authRequest);

    Employee findById(Long id);

    Employee findByUSername(String username);
}
