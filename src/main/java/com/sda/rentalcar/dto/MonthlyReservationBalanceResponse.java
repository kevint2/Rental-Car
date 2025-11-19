package com.sda.rentalcar.dto;

public class MonthlyReservationBalanceResponse {
    private final int year;
    private final int month;
    private final long reservationCount;
    private final double totalAmount;

    public MonthlyReservationBalanceResponse(int year, int month, long reservationCount, double totalAmount) {
        this.year = year;
        this.month = month;
        this.reservationCount = reservationCount;
        this.totalAmount = totalAmount;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public long getReservationCount() {
        return reservationCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
