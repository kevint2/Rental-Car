package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.Costumer;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.CostumerRepository;
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
@Override
public Costumer create(Costumer costumer){
   if (costumer.getCostumerId()==null){
      return costumerRepository.save(costumer);
   }else {
      throw GenericException.idISNotnull();
   }
}
@Override
public Costumer update(Costumer costumer){
   if (costumer.getCostumerId()!=null){
      return costumerRepository.save(costumer);
   }else {
      throw GenericException.idIsNull();
   }
}
@Override
   public List<Costumer>findAll(){
   return costumerRepository.findAll();
}
}
