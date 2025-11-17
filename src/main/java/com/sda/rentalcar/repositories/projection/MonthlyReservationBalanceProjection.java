package com.sda.rentalcar.repositories.projection;

public interface MonthlyReservationBalanceProjection {
    Integer getYear();
    Integer getMonth();
    Long getTotalReservations();
    Double getTotalAmount();
}
