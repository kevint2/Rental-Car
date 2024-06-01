package com.sda.rentalcar.services;

import com.sda.rentalcar.entities.Reservation;

public interface ReservationService {
    Reservation create(Reservation reservation, Long carId, String costumerEmail, String loanComment);

    Reservation returnCar(Long reservationId, Long branchId);

    void  cancelReservation(Long reservationId);
}
