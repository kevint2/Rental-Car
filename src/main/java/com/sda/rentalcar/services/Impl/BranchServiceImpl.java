package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Branch;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.BranchRepository;
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
    @Override
    public Branch create(Branch branch){
        if (branch.getId()==null){
            return branchRepository.save(branch);
        }else {
            throw GenericException.idISNotnull();
        }
    }
    @Override
    public Branch update(Branch branch){
        if (branch.getId()!=null){
            return branchRepository.save(branch);
        }else {
            throw GenericException.idIsNull();
        }
    }
    @Override
    public List<Branch> showAll(){
        return branchRepository.findAll();
    }
}
