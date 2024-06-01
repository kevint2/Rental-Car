package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Loan;
import com.sda.rentalcar.repositories.LoanRepository;
import com.sda.rentalcar.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public Loan create (Loan loan){
        return loanRepository.save(loan);
    }

}
