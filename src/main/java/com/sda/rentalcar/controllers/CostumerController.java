package com.sda.rentalcar.controllers;

import com.sda.rentalcar.entities.Costumer;
import com.sda.rentalcar.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/costumer")
public class CostumerController {
    @Autowired
    private CostumerService costumerService;

    @PostMapping("/create")
    public Costumer create (@RequestBody Costumer costumer){
        return costumerService.createOrUpdate(costumer);
    }
    @GetMapping("/findByEmail")
    public Costumer findByEmail(@RequestParam String costumerEmail){
        return costumerService.findByEmail(costumerEmail);
    }
    @GetMapping("/getAllCostumers")
    public List<Costumer>getAllCostumers(@RequestParam Long rentalId){
        return costumerService.getAllCostumersByRental(rentalId);
    }
}
