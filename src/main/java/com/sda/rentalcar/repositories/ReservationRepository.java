package com.sda.rentalcar.repositories;

import com.sda.rentalcar.entities.Reservation;
import com.sda.rentalcar.repositories.projection.MonthlyCancellationBalanceProjection;
import com.sda.rentalcar.repositories.projection.MonthlyReservationBalanceProjection;
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

     @Query("SELECT function('year', r.bookingDate) AS year, function('month', r.bookingDate) AS month, " +
             "COUNT(r) AS totalReservations, COALESCE(SUM(r.amount), 0) AS totalAmount " +
             "FROM Reservation r " +
             "WHERE r.cancelled = false AND r.bookingDate IS NOT NULL " +
             "GROUP BY function('year', r.bookingDate), function('month', r.bookingDate) " +
             "ORDER BY year, month")
     List<MonthlyReservationBalanceProjection> findMonthlyReservationBalances();

     @Query("SELECT function('year', r.cancellationDate) AS year, function('month', r.cancellationDate) AS month, " +
             "COUNT(r) AS totalCancellations, COALESCE(SUM(r.cancellationPenalty), 0) AS totalPenalty " +
             "FROM Reservation r " +
             "WHERE r.cancelled = true AND r.cancellationDate IS NOT NULL " +
             "GROUP BY function('year', r.cancellationDate), function('month', r.cancellationDate) " +
             "ORDER BY year, month")
     List<MonthlyCancellationBalanceProjection> findMonthlyCancellationBalances();
}
