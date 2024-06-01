package com.sda.rentalcar.controllers;

import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;
    @PostMapping("/create")
    public Branch create (@RequestBody Branch branch, @RequestParam Long rentalId){
        return branchService.create(branch,rentalId);
    }
    @GetMapping("/getAllBranches")
    public List<Branch>getAllBranches(@RequestParam Long rentalId){
        return branchService.showAllByRental(rentalId);
    }
}

