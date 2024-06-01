package com.sda.rentalcar.controllers;

import com.sda.rentalcar.dto.AuthRequest;
import com.sda.rentalcar.entities.Employee;
import com.sda.rentalcar.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/createEmployee")
    public Employee createEmployee (@RequestBody Employee employee, @RequestParam Long branchId){
        return employeeService.create(employee,false,branchId);
    }
    @PostMapping("/createManager")
    public Employee createManager (@RequestBody Employee employee, @RequestParam Long branchId){
        return employeeService.create(employee,true,branchId);
    }

    @PostMapping("/createOwner")
    public Employee createOwner(@RequestBody Employee employee){
        return employeeService.createOwner(employee);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return employeeService.login(authRequest);
    }

}
