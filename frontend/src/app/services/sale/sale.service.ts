import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sale } from '../../interfaces/sale';

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllSales(page: number, size: number): Observable<Sale[]> {
    const data = this.http.get<Sale[]>(this.API_URL + "/api/v1/sales?page=" + page + "&size=" + size);
    return data ?? [];
  }

  getFilteredSales(page: number, size: number, date: string, minPrice: number, maxPrice: number, carName: string, customerName: string, employeeName: string): Observable<Sale[]> {
    const data = this.http.get<Sale[]>(this.API_URL + "/api/v1/sales/filter?page=" + page + "&size=" + size + "&date=" + date + "&min_price=" + minPrice + "&max_price=" + maxPrice + "&car_name=" + carName + "&customer_name=" + customerName + "&employee_name=" + employeeName);
    return data ?? [];
  }
}
