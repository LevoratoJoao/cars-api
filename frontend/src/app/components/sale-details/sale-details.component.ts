import { Component, Input } from '@angular/core';
import { Sale } from '../../interfaces/sale';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sale-details',
  imports: [CommonModule],
  templateUrl: './sale-details.component.html',
  styleUrl: './sale-details.component.css'
})
export class SaleDetailsComponent {
  @Input() sale!: Sale;
}
