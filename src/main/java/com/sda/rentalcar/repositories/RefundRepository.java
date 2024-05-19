package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository  extends JpaRepository<Refund,Long > {
}
