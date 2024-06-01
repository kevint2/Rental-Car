package com.sda.rentalcar.services;

import com.sda.rentalcar.dto.AuthRequest;
import com.sda.rentalcar.entities.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    Employee create(Employee employee, boolean isManager,Long branchId);



    ResponseEntity<?> login(AuthRequest authRequest);

    Employee createOwner(Employee employee);

    Employee findById(Long id);

    Employee findByUsername(String username);

    Employee findEmployeeLoggedIn();
}
