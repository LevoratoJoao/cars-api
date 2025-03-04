import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Manufacturer } from '../../interfaces/manufacturer';

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllManufacturers(): Observable<Manufacturer[]> {
    const data = this.http.get<Manufacturer[]>(this.API_URL + "/api/v1/manufacturers");
    return data;
  }
}
