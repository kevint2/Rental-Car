package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Revenue;

public interface RevenueService {
    Revenue createOrUpdate(Long rentalId, Double amount);
}
