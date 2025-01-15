import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../interfaces/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  readonly API_URL = "http://localhost:8080"

  constructor(private http: HttpClient) {
    console.log("Connecting to the backend");
  }

  getAllCars(): Observable<Car[]> {
    console.log("Getting all cars in " + this.API_URL + "/api/v1/cars");
    //const token = localStorage.getItem('token')!; duknow how to do this :/
    const data = this.http.get<Car[]>(this.API_URL + "/api/v1/cars");
    return data ?? [];
  }
}
