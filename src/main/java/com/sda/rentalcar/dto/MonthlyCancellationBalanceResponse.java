package com.sda.rentalcar.dto;

public class MonthlyCancellationBalanceResponse {
    private final int year;
    private final int month;
    private final long cancellationCount;
    private final double totalPenalty;

    public MonthlyCancellationBalanceResponse(int year, int month, long cancellationCount, double totalPenalty) {
        this.year = year;
        this.month = month;
        this.cancellationCount = cancellationCount;
        this.totalPenalty = totalPenalty;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public long getCancellationCount() {
        return cancellationCount;
    }

    public double getTotalPenalty() {
        return totalPenalty;
    }
}
