package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Branch;

import java.util.List;

public interface BranchService {
    Branch create(Branch branch ,Long id);



    List<Branch> showAllByRental(Long rentalId);
}
