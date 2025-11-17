import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { CarResponse } from '../services/types';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  cars: CarResponse[] = [];
  carStatuses = ['AVAILABLE', 'BOOKED', 'UNAVAILABLE'];
  loading = false;
  message = '';

  carForm = this.fb.group({
    brand: ['', Validators.required],
    model: ['', Validators.required],
    bodyType: ['', Validators.required],
    year: [2024, [Validators.required, Validators.min(1950)]],
    color: ['', Validators.required],
    mileage: [0, [Validators.required, Validators.min(0)]],
    amount: [0, [Validators.required, Validators.min(0)]],
    imageUrl: [''],
    branchId: [1, Validators.required]
  });

  mileageForm = this.fb.group({
    carId: ['', Validators.required],
    mileage: [0, [Validators.required, Validators.min(0)]]
  });

  statusForm = this.fb.group({
    carId: ['', Validators.required],
    status: ['AVAILABLE', Validators.required]
  });

  constructor(private fb: FormBuilder, private api: ApiService) {}

  ngOnInit(): void {
    this.loadCars();
  }

  loadCars(): void {
    this.loading = true;
    this.api.getCars().subscribe({
      next: cars => {
        this.cars = cars;
        this.loading = false;
      },
      error: () => {
        this.message = 'Nuk mund të ngarkohen makinat.';
        this.loading = false;
      }
    });
  }

  submitCar(): void {
    if (this.carForm.invalid) {
      return;
    }
    this.api.createCar(this.carForm.getRawValue() as any).subscribe({
      next: () => {
        this.message = 'Makina u shtua me sukses.';
        this.carForm.reset({ year: 2024, mileage: 0, amount: 0, status: 'AVAILABLE' });
        this.loadCars();
      },
      error: () => (this.message = 'Shtimi dështoi, kontrollo të dhënat.')
    });
  }

  submitMileage(): void {
    if (this.mileageForm.invalid) {
      return;
    }
    const { carId, mileage } = this.mileageForm.getRawValue();
    this.api.updateMileage(Number(carId), Number(mileage)).subscribe({
      next: () => {
        this.message = 'Kilometrat u përditësuan.';
        this.loadCars();
      },
      error: () => (this.message = 'Përditësimi i kilometrave dështoi.')
    });
  }

  submitStatus(): void {
    if (this.statusForm.invalid) {
      return;
    }
    const { carId, status } = this.statusForm.getRawValue();
    this.api.updateStatus(Number(carId), String(status)).subscribe({
      next: () => {
        this.message = 'Statusi u ndryshua.';
        this.loadCars();
      },
      error: () => (this.message = 'Ndryshimi i statusit dështoi.')
    });
  }
}
