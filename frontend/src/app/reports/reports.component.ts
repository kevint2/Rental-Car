import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import {
  MonthlyCancellationBalanceResponse,
  MonthlyReservationBalanceResponse
} from '../services/types';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  completed: MonthlyReservationBalanceResponse[] = [];
  cancelled: MonthlyCancellationBalanceResponse[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.api.getCompletedBalances().subscribe(data => (this.completed = data));
    this.api.getCancelledBalances().subscribe(data => (this.cancelled = data));
  }

  monthName(month: number): string {
    return new Date(2023, month - 1, 1).toLocaleString('sq-AL', { month: 'long' });
  }
}
