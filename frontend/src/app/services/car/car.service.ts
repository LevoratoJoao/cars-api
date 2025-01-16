import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { min, Observable } from 'rxjs';
import { Car } from '../../interfaces/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllCars(): Observable<Car[]> {
    //const token = localStorage.getItem('token')!; duknow how to do this :/
    const data = this.http.get<Car[]>(this.API_URL + "/api/v1/cars");
    return data ?? [];
  }

  getFilteredCars(manu: string, color: string, model: string, minPrice: number, maxPrice: number): Observable<Car[]> {

    const data = this.http.get<Car[]>(this.API_URL + "/api/v1/cars/filter?manufacturer=" + manu + "&color=" + color + "&model=" + model + "&min_price=" + minPrice + "&max_price=" + maxPrice);
    return data;
  }
}
