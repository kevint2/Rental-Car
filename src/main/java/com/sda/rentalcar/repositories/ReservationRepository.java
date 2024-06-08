package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation>findAllByCar_IdAndDateFromIsAfter(Long carId , LocalDate localDateNow);
    List<Reservation>findAllByDateFromIsAfter(LocalDate localDate);
    List<Reservation> getAllByDateToIsBetween(LocalDate localDate,LocalDate localDate1);
    List<Reservation>getAllByBranchDepartmentNull();
     @Query("select a from Reservation as a where a.dateTo between local date  and local date ")
     List<Reservation>findAllByDate(LocalDate localDate,LocalDate localDate1);
}
