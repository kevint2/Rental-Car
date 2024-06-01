package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Rental;
import com.sda.rentalcar.entities.Revenue;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.RentalRepository;
import com.sda.rentalcar.repositories.RevenueRepository;
import com.sda.rentalcar.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Revenue createOrUpdate(Long rentalId, Double amount){
        Revenue revenue = revenueRepository.findByRental_Id(rentalId)
                .orElse(new Revenue());
        if (revenue.getIncome() != 0.0)
            revenue.setIncome(revenue.getIncome() + amount);
        else {
            Rental rental = rentalRepository.findById(rentalId)
                            .orElseThrow(()-> GenericException.notFound(rentalId));
            revenue.setRental(rental);
            revenue.setIncome(amount);
        }
        return revenueRepository.save(revenue);
    }

}