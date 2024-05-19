package com.sda.rentalcar.security;


import com.sda.rentalcar.entities.Employee;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class UserDetailServiceImpl implements UserDetailsService {
@Autowired
private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> user =  employeeRepository.findByUsername(username);
        if (user.isPresent()){
            return new UserDetailImpl(user.get());
        }else {
            throw GenericException.notFound(username);
        }
    }
}
