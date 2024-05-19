package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Branch;

import java.util.List;

public interface BranchService {
    Branch create(Branch branch);

    Branch update(Branch branch);

    List<Branch> showAll();
}
