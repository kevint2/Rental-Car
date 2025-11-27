import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { Reservation } from '../services/types';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  reservations: Reservation[] = [];
  message = '';

  reservationForm = this.fb.group({
    carId: ['', Validators.required],
    costumerEmail: ['', [Validators.required, Validators.email]],
    comment: [''],
    dateFrom: ['', Validators.required],
    dateTo: ['', Validators.required]
  });

  returnForm = this.fb.group({
    reservationId: ['', Validators.required],
    branchId: ['', Validators.required]
  });

  cancelForm = this.fb.group({
    reservationId: ['', Validators.required]
  });

  extendForm = this.fb.group({
    reservationId: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    days: [1, [Validators.required, Validators.min(1)]]
  });

  constructor(private fb: FormBuilder, private api: ApiService) {}

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.api.getReservations().subscribe({
      next: reservations => (this.reservations = reservations),
      error: () => (this.message = 'Could not load reservations.')
    });
  }

  submitReservation(): void {
    if (this.reservationForm.invalid) {
      return;
    }
    const { carId, costumerEmail, comment, dateFrom, dateTo } = this.reservationForm.getRawValue();
    this.api
      .createReservation({ dateFrom: String(dateFrom), dateTo: String(dateTo) }, Number(carId), String(costumerEmail), String(comment || ''))
      .subscribe({
        next: () => {
          this.message = 'Reservation created.';
          this.loadReservations();
        },
        error: () => (this.message = 'Creating the reservation failed.')
      });
  }

  submitReturn(): void {
    if (this.returnForm.invalid) {
      return;
    }
    const { reservationId, branchId } = this.returnForm.getRawValue();
    this.api.returnCar(Number(reservationId), Number(branchId)).subscribe({
      next: () => {
        this.message = 'Car returned.';
        this.loadReservations();
      },
      error: () => (this.message = 'Return failed.')
    });
  }

  submitCancel(): void {
    if (this.cancelForm.invalid) {
      return;
    }
    const { reservationId } = this.cancelForm.getRawValue();
    this.api.cancelReservation(Number(reservationId)).subscribe({
      next: () => {
        this.message = 'Reservation cancelled.';
        this.loadReservations();
      },
      error: () => (this.message = 'Cancellation failed.')
    });
  }

  submitExtend(): void {
    if (this.extendForm.invalid) {
      return;
    }
    const { reservationId, email, days } = this.extendForm.getRawValue();
    this.api.extendReservation(Number(reservationId), String(email), Number(days)).subscribe({
      next: () => {
        this.message = 'Reservation extended.';
        this.loadReservations();
      },
      error: () => (this.message = 'Extension failed.')
    });
  }
}
