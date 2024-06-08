package com.sda.rentalcar.services.Impl;

import com.sda.rentalcar.entities.*;
import com.sda.rentalcar.exceptions.GenericException;
import com.sda.rentalcar.repositories.*;
import com.sda.rentalcar.services.*;
import com.sda.rentalcar.static_data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
            if (ChronoUnit.DAYS.between(LocalDate.now(),reservation.getDateFrom())<=2){
               revenueService.createOrUpdate(reservation.getBranchLoan().getRental().getId(),reservation.getAmount()*-0.8);
            }else {
                revenueService.createOrUpdate(reservation.getBranchLoan().getRental().getId(),reservation.getAmount()*-1);
            }
        }else {
            throw GenericException.notFound(reservationId);
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
}
