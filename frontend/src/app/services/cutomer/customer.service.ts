import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../../interfaces/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllCustomers(page: number, size: number): Observable<Customer[]> {
    const data = this.http.get<Customer[]>(this.API_URL + "/api/v1/customers?page=" + page + "&size=" + size);
    return data ?? [];
  }

  getFilteredCustomers(page: number, size: number, first_name: string, last_name: string, phone_number: string, email: string): Observable<Customer[]> {
    const data = this.http.get<Customer[]>(this.API_URL + "/api/v1/customers/filter?page=" + page + "&size=" + size + "&first_name=" + first_name + "&last_name=" + last_name + "&phone_number=" + phone_number + "&email=" + email);
    return data ?? [];
  }
}
