package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {
}
