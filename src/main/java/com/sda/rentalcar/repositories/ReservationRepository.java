package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation>findAllByCar_IdAndDateFromIsAfter(Long carId , LocalDate localDateNow);
    List<Reservation>findAllByDateFromIsAfter(LocalDate localDate);
}
