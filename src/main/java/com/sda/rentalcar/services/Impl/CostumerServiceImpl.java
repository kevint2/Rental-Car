package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Costumer;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.CostumerRepository;
import com.sda.rentalcar.repositories.RentalRepository;
import com.sda.rentalcar.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CostumerServiceImpl implements CostumerService {
@Autowired
   private CostumerRepository costumerRepository;
@Autowired
private RentalRepository rentalRepository;
@Override
public Costumer createOrUpdate(Costumer costumer){
      return costumerRepository.save(costumer);
}

@Override
public Costumer findByEmail(String email){
   return  costumerRepository.findByEmail(email);
}
@Override
   public List<Costumer>findAll(){
   return costumerRepository.findAll();
}
@Override
    public List<Costumer>getAllCostumersByRental(Long rentalId){
if (rentalRepository.findById(rentalId).isPresent()){
    return costumerRepository.findCostumerByRentalId(rentalId);
}else {
    throw GenericException.notFound(rentalId);
}
    }
}

