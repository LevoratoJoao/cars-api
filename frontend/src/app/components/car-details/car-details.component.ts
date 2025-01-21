import { Component, Input } from '@angular/core';
import { Car } from '../../interfaces/car';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-car-details',
  imports: [CommonModule],
  templateUrl: './car-details.component.html',
  styleUrl: './car-details.component.css'
})
export class CarDetailsComponent {
  @Input() car!: Car;
}
