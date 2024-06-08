package com.sda.rentalcar.services.Impl;
import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.entities.Rental;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.BranchRepository;
import com.sda.rentalcar.repositories.RentalRepository;
import com.sda.rentalcar.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Branch create(Branch branch, Long rentalId) {
        if (rentalRepository.findById(rentalId).isPresent()) {
            Rental rental = rentalRepository.findById(rentalId).get();
            branch.setRental(rental);
            branch.setActive(true);
            return branchRepository.save(branch);
        } else {
            throw GenericException.idISNotnull();
        }
    }

    @Override
    public List<Branch> showAllByRental(Long rentalId) {
        return branchRepository.findAllByRentalId(rentalId);
    }

    @Override
    public void closeBranch(Long branchId){
        Branch branch = branchRepository.findById(branchId).orElseThrow(()->GenericException.notFound(branchId));
        branch.setActive(false);
    }
}
