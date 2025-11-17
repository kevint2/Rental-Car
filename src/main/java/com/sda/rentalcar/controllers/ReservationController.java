package com.sda.rentalcar.controllers;


import com.sda.rentalcar.dto.MonthlyCancellationBalanceResponse;
import com.sda.rentalcar.dto.MonthlyReservationBalanceResponse;
import com.sda.rentalcar.entities.Reservation;
import com.sda.rentalcar.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public Reservation create (@RequestBody Reservation reservation, @RequestParam Long carId,@RequestParam String costumerEmail,@RequestParam String loanComment){
     return    reservationService.create(reservation,carId,costumerEmail,loanComment);
    }
    @PostMapping("/returnCar")
    public Reservation returnCar(@RequestParam Long reservationId,@RequestParam Long branchId){
        return reservationService.returnCar(reservationId,branchId);
    }
    @PostMapping("/cancelReservation")
    public void cancelReservation(@RequestParam Long reservationId){
        reservationService.cancelReservation(reservationId);
    }

    @PostMapping("/extendReservation")
    public Reservation extendReservation(@RequestParam String email, @RequestParam Long id, @RequestParam Integer days){
        return reservationService.extendReservation(email, id, days);
    }

    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @GetMapping("/balance/completed")
    public List<MonthlyReservationBalanceResponse> getMonthlyReservationBalances() {
        return reservationService.getMonthlyReservationBalances();
    }

    @GetMapping("/balance/cancelled")
    public List<MonthlyCancellationBalanceResponse> getMonthlyCancellationBalances() {
        return reservationService.getMonthlyCancellationBalances();
    }
}
