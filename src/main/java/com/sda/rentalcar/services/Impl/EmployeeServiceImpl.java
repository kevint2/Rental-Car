package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.dto.AuthRequest;
import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.entities.Employee;
import com.sda.rentalcar.entities.Position;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.BranchRepository;
import com.sda.rentalcar.repositories.EmployeeRepository;
import com.sda.rentalcar.repositories.PositionRepository;
import com.sda.rentalcar.repositories.RentalRepository;
import com.sda.rentalcar.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Employee create(Employee employee, boolean isManager,Long branchId) {
        if (employee.getEmployeeId() == null) {
            if (!employeeRepository.existsEmployeeByUsername(employee.getUsername())) {
                if (!isManager) {
                    Position position = positionRepository.findById("ROLE_EMPLOYEE").get();
                   employee.setPosition(position);
                } else {
                    Position position = positionRepository.findById("ROLE_MANAGER").get();
                    employee.setPosition(position);
                }
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
                Branch branch = branchRepository.findById(branchId).get();
                employee.setBranch(branch);
                return employeeRepository.save(employee);
            } else {
                throw GenericException.UsernameExist(employee.getUsername());
            }
        } else {
            throw GenericException.idISNotnull();
        }
    }


    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(authentication.isAuthenticated());
        } catch (LockedException lockedException) {
            return ResponseEntity.status(403).body(lockedException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @Override
    public Employee createOwner(Employee employee){
        Position position = positionRepository.findById("ROLE_OWNER").get();
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setPosition(position);
        return employeeRepository.save(employee);
    }
    @Override
    public Employee findById(Long id){
        return employeeRepository.findById(id).orElseThrow(()-> GenericException.notFound(id));
    }
    @Override
    public Employee findByUsername(String username){
        return employeeRepository.findByUsername(username).orElseThrow(()->GenericException.notFound(username));
    }

    @Override
    public Employee findEmployeeLoggedIn(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }

}



