import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../../interfaces/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllCars(page: number, size: number): Observable<Car[]> {
    //const token = localStorage.getItem('token')!; duknow how to do this :/
    const data = this.http.get<Car[]>(this.API_URL + "/api/v1/cars?page=" + page + "&size=" + size);
    return data ?? [];
  }

  getFilteredCars(page: number, size: number, manu: string, color: string, model: string, minPrice: number, maxPrice: number, carName: string): Observable<Car[]> {
    const data = this.http.get<Car[]>(this.API_URL + "/api/v1/cars/filter?page=" + page + "&size=" + size + "&manufacturer=" + manu + "&color=" + color + "&model=" + model + "&min_price=" + minPrice + "&max_price=" + maxPrice + "&car=" + carName);
    return data;
  }

  getLength(): Observable<number> {
    const data = this.http.get<number>(this.API_URL + "/api/v1/cars/length");
    return data;
  }
}
