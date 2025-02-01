import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from '../../interfaces/employee';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllEmployees(page: number, size: number) {
    const data =this.http.get<Employee[]>(this.API_URL + "/api/v1/employees?page=" + page + "&size=" + size);
    return data ?? [];
  }

  getFilteredEmployees(page: number, size: number, firstName: string, lastName: string, position: string, email: string, phoneNumber: string, minSalary: number, maxSalary: number): Observable<Employee[]> {
    const data = this.http.get<Employee[]>(this.API_URL + "/api/v1/employees/filter?page=" + page + "&size=" + size + "&first_name=" + firstName + "&last_name=" + lastName + "&position=" + position + "&email=" + email + "&phone_number=" + phoneNumber + "&min_salary=" + minSalary + "&max_salary=" + maxSalary);
    return data;
  }
}
