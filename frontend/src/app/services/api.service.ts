import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import {
  CarResponse,
  CreateCarRequest,
  Reservation,
  ReservationPayload,
  MonthlyCancellationBalanceResponse,
  MonthlyReservationBalanceResponse
} from './types';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCars(): Observable<CarResponse[]> {
    return this.http.get<CarResponse[]>(`${this.baseUrl}/car/all`);
  }

  createCar(payload: CreateCarRequest): Observable<CarResponse> {
    return this.http.post<CarResponse>(`${this.baseUrl}/car/create`, payload);
  }

  updateMileage(carId: number, mileage: number): Observable<CarResponse> {
    const params = new HttpParams().set('carId', carId);
    return this.http.put<CarResponse>(`${this.baseUrl}/car/mileage`, { mileage }, { params });
  }

  updateStatus(carId: number, status: string): Observable<CarResponse> {
    const params = new HttpParams().set('carId', carId);
    return this.http.put<CarResponse>(`${this.baseUrl}/car/status`, { status }, { params });
  }

  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseUrl}/reservation`);
  }

  createReservation(payload: ReservationPayload, carId: number, email: string, comment: string): Observable<Reservation> {
    const params = new HttpParams()
      .set('carId', carId)
      .set('costumerEmail', email)
      .set('loanComment', comment);
    return this.http.post<Reservation>(`${this.baseUrl}/reservation/create`, payload, { params });
  }

  cancelReservation(reservationId: number): Observable<void> {
    const params = new HttpParams().set('reservationId', reservationId);
    return this.http.post<void>(`${this.baseUrl}/reservation/cancelReservation`, null, { params });
  }

  returnCar(reservationId: number, branchId: number): Observable<Reservation> {
    const params = new HttpParams().set('reservationId', reservationId).set('branchId', branchId);
    return this.http.post<Reservation>(`${this.baseUrl}/reservation/returnCar`, null, { params });
  }

  extendReservation(reservationId: number, email: string, days: number): Observable<Reservation> {
    const params = new HttpParams().set('id', reservationId).set('email', email).set('days', days);
    return this.http.post<Reservation>(`${this.baseUrl}/reservation/extendReservation`, null, { params });
  }

  getCompletedBalances(): Observable<MonthlyReservationBalanceResponse[]> {
    return this.http.get<MonthlyReservationBalanceResponse[]>(`${this.baseUrl}/reservation/balance/completed`);
  }

  getCancelledBalances(): Observable<MonthlyCancellationBalanceResponse[]> {
    return this.http.get<MonthlyCancellationBalanceResponse[]>(`${this.baseUrl}/reservation/balance/cancelled`);
  }
}
