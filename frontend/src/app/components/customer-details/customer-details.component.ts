import { Component, Input } from '@angular/core';
import { Customer } from '../../interfaces/customer';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-details',
  imports: [CommonModule],
  templateUrl: './customer-details.component.html',
  styleUrl: './customer-details.component.css'
})
export class CustomerDetailsComponent {
  @Input() customer!: Customer;
}
