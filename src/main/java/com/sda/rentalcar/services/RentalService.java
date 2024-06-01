package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Rental;

public interface RentalService {
    Rental create(Rental rental,String ownerUsername );

    Rental findById(Long id);
}
