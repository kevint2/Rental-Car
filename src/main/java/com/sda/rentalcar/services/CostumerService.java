package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Costumer;

import java.util.List;

public interface CostumerService {
    Costumer createOrUpdate(Costumer costumer);


    Costumer findByEmail(String email);

    List<Costumer> findAll();

    List<Costumer>getAllCostumersByRental(Long rentalId);
}
