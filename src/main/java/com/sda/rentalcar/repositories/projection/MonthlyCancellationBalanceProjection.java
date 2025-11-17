package com.sda.rentalcar.repositories.projection;

public interface MonthlyCancellationBalanceProjection {
    Integer getYear();
    Integer getMonth();
    Long getTotalCancellations();
    Double getTotalPenalty();
}
