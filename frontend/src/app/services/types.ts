export interface CarResponse {
  id: number;
  brand: string;
  model: string;
  bodyType: string;
  year: number;
  color: string;
  mileage: number;
  status: 'BOOKED' | 'AVAILABLE' | 'UNAVAILABLE';
  amount: number;
  imageUrl?: string;
  branchId?: number;
}

export interface CreateCarRequest {
  brand: string;
  model: string;
  bodyType: string;
  year: number;
  color: string;
  mileage: number;
  amount: number;
  imageUrl?: string;
  branchId: number;
}

export interface ReservationPayload {
  dateFrom: string;
  dateTo: string;
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface Reservation {
  id: number;
  bookingDate: string;
  dateFrom: string;
  dateTo: string;
  amount: number;
  cancelled: boolean;
  cancellationDate?: string;
  cancellationPenalty?: number;
}

export interface MonthlyReservationBalanceResponse {
  year: number;
  month: number;
  totalReservations: number;
  totalAmount: number;
}

export interface MonthlyCancellationBalanceResponse {
  year: number;
  month: number;
  totalCancellations: number;
  totalPenalty: number;
}
