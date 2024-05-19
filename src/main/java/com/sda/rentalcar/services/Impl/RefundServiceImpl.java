package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Employee;
import com.sda.rentalcar.entities.Refund;
import com.sda.rentalcar.repositories.RefundRepository;
import com.sda.rentalcar.services.EmployeeService;
import com.sda.rentalcar.services.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public Refund createOrUpdate(Refund refund) {
        Employee employee = employeeService.findEmployeeLoggedIn();
        refund.setEmployee(employee);
        return refundRepository.save(refund);
    }
}
