package com.sda.rentalcar.services;

import com.sda.rentalcar.dto.MonthlyCancellationBalanceResponse;
import com.sda.rentalcar.dto.MonthlyReservationBalanceResponse;
import com.sda.rentalcar.entities.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation create(Reservation reservation, Long carId, String costumerEmail, String loanComment);

    Reservation returnCar(Long reservationId, Long branchId);

    void  cancelReservation(Long reservationId);

    Reservation extendReservation(String email, Long id, Integer days);

    List<MonthlyReservationBalanceResponse> getMonthlyReservationBalances();

    List<MonthlyCancellationBalanceResponse> getMonthlyCancellationBalances();

    List<Reservation> findAll();
}
