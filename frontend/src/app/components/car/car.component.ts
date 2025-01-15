import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Car } from '../../interfaces/car';
import { CarService } from '../../services/car.service';

@Component({
  selector: 'app-car',
  imports: [CommonModule],
  templateUrl: './car.component.html',
  styleUrl: './car.component.css'
})
export class CarComponent {
  carsList: Car[] = [];

  constructor(private carService: CarService) {
    console.log("Starting connection");
  }

  ngOnInit(): void {
    console.log("Trying to get all cars");
    this.carService.getAllCars().subscribe(
      (carsList: Car[]) => {
        this.carsList = carsList;
        console.log(carsList);
      }
    )
  }
}
