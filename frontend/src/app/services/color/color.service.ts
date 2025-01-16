import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Color } from '../../interfaces/color';

@Injectable({
  providedIn: 'root'
})
export class ColorService {
  readonly API_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllColors(): Observable<Color[]> {
    const data = this.http.get<Color[]>(this.API_URL + "/api/v1/colors");
    return data;
  }
}
