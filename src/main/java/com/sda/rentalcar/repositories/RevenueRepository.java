package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Optional<Revenue> findByRental_Id(Long rentalId);

}