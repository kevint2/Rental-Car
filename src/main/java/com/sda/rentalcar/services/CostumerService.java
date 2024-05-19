package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Costumer;

import java.util.List;

public interface CostumerService {
    Costumer create(Costumer costumer);

    Costumer update(Costumer costumer);

    List<Costumer> findAll();
}
