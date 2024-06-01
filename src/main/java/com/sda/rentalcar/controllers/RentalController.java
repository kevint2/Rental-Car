package com.sda.rentalcar.controllers;

import com.sda.rentalcar.entities.Rental;
import com.sda.rentalcar.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental")
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @PostMapping("/create")
    public Rental create (@RequestBody Rental rental,@RequestParam String ownerUsername){
        return rentalService.create(rental,ownerUsername);
    }
}
