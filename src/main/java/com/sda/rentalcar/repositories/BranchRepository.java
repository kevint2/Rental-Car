package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch , Long> {
}
