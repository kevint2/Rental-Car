package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,String> {
}
