package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.dto.MonthlyCancellationBalanceResponse;
import com.sda.rentalcar.dto.MonthlyReservationBalanceResponse;
import com.sda.rentalcar.entities.*;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.*;
import com.sda.rentalcar.services.*;
import com.sda.rentalcar.static_data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableScheduling
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RefundService refundService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    private RevenueService revenueService;
@Override
    public Reservation create(Reservation reservation, Long carId, String costumerEmail, String loanComment) {
        if (carRepository.findById(carId).isPresent() ) {
            List<Reservation> reservations = reservationRepository.findAllByCar_IdAndDateFromIsAfter(carId, LocalDate.now());
            AtomicBoolean result = new AtomicBoolean(false);
            reservations.forEach(reservation1 -> {
                if (isTimeWrong(reservation1, reservation.getDateFrom(), reservation.getDateTo())) {
                    result.set(true);
                }
            });

            if (!result.get()) {
                Car car = carRepository.findById(carId).get();
                reservation.setCar(car);
                reservation.setBranchLoan(car.getBranch());
                Costumer costumer = costumerRepository.findByEmail(costumerEmail);
                costumer.setRental(car.getBranch().getRental());
                reservation.setCostumer(costumer);
                reservation.setBookingDate(LocalDate.now());
                reservation.setCancelled(false);
                reservation.setCancellationDate(null);
                reservation.setCancellationPenalty(null);
                reservation.setAmount(
                        (ChronoUnit.DAYS.between(reservation.getDateFrom()
                                , reservation.getDateTo()) * car.getAmount()));
                Loan loan = new Loan();
                loan.setReservation(reservation);
                loan.setEmployee(employeeService.findEmployeeLoggedIn());
                loan.setDateOfRental(reservation.getDateFrom());
                loan.setComment(loanComment);
               loanService.create(loan);
                 reservationRepository.save(reservation);
                revenueService.createOrUpdate(car.getBranch().getRental().getId(), reservation.getAmount());
                return reservation;
            } else {
                throw GenericException.timeIsWrong();
            }

        } else {
            throw GenericException.notFound(carId);
        }
    }
    @Override
    public Reservation returnCar(Long reservationId ,Long branchId){
        if (reservationRepository.findById(reservationId).isPresent()){
            Reservation reservation = reservationRepository.findById(reservationId).get();
            Branch branch = branchRepository.findById(branchId).orElseThrow(
                    ()-> GenericException.notFound(branchId)
            );
            reservation.setBranchDepartment(branch);
            Car car = reservation.getCar();
            car.setStatus(Status.AVAILABLE);
            car.setBranch(branch);
            if (ChronoUnit.DAYS.between(reservation.getDateTo(),LocalDate.now())!=0){
                Refund refund = new Refund();
                refund.setReservation(reservation);
                refund.setRefundDateOfReturn(LocalDate.now());
                refund.setSurcharge(ChronoUnit.DAYS.between(reservation.getDateTo(),LocalDate.now()) * car.getAmount());
                refundService.createOrUpdate(refund);
                revenueService.createOrUpdate(branch.getRental().getId(),refund.getSurcharge());
           } return reservationRepository.save(reservation);
        }else {
            throw GenericException.notFound(reservationId);
        }
    }
    @Override
    public void  cancelReservation(Long reservationId ){
        if (reservationRepository.findById(reservationId).isPresent()){
            Reservation reservation = reservationRepository.findById(reservationId).get();
            reservation.getCar().setStatus(Status.AVAILABLE);
            Double penalty;
            if (ChronoUnit.DAYS.between(LocalDate.now(),reservation.getDateFrom())<=2){
                penalty = reservation.getAmount()*-0.8;
            }else {
                penalty = reservation.getAmount()*-1;
            }
            reservation.setCancelled(true);
            reservation.setCancellationDate(LocalDate.now());
            reservation.setCancellationPenalty(penalty);
            revenueService.createOrUpdate(reservation.getBranchLoan().getRental().getId(),penalty);
            reservationRepository.save(reservation);
        }else {
            throw GenericException.notFound(reservationId);
        }

    }

    @Override
    public Reservation extendReservation(String email, Long id, Integer days){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> GenericException.notFound(id));
        if (reservation.getCostumer().getEmail().equals(email)){
            Car car = reservation.getCar();
            List<Reservation> reservations = reservationRepository.findAllByCar_IdAndDateFromIsAfter(car.getId(), reservation.getDateFrom());
            reservations.forEach(r-> {
                if (r.getDateFrom().equals(reservation.getDateFrom().plusDays(days))||
                r.getDateFrom().isBefore(reservation.getDateFrom().plusDays(days))) {
                    throw new RuntimeException("This car is reserved in this days.");
                }
            });
                reservation.setDateTo(reservation.getDateTo().plusDays(days));
                 reservationRepository.save(reservation);
                revenueService.createOrUpdate(reservation.getBranchLoan().getRental().getId(),days*car.getAmount());
                return reservation;
        } else {
            throw new RuntimeException("This reservation is not made by a costumer.");
        }

    }


    private Boolean isTimeWrong(Reservation reservation, LocalDate start, LocalDate end) {
        return ((start.isAfter(reservation.getDateFrom()) && start.isBefore(reservation.getDateTo()))
                || (end.isAfter(reservation.getDateFrom()) && end.isBefore(reservation.getDateTo()))
                || (start.isBefore(reservation.getDateFrom()) && end.isAfter(reservation.getDateTo()))
                || (start.equals(reservation.getDateFrom()) || end.equals(reservation.getDateTo()))
                || start.isAfter(end)
                || start.isAfter(LocalDate.now()));
    }

    @Override
    public List<MonthlyReservationBalanceResponse> getMonthlyReservationBalances() {
        return reservationRepository.findMonthlyReservationBalances().stream()
                .map(result -> new MonthlyReservationBalanceResponse(
                        result.getYear(),
                        result.getMonth(),
                        result.getTotalReservations(),
                        result.getTotalAmount()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyCancellationBalanceResponse> getMonthlyCancellationBalances() {
        return reservationRepository.findMonthlyCancellationBalances().stream()
                .map(result -> new MonthlyCancellationBalanceResponse(
                        result.getYear(),
                        result.getMonth(),
                        result.getTotalCancellations(),
                        result.getTotalPenalty()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }


}
