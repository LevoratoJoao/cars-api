import { Component, Input, input } from '@angular/core';
import { __importDefault } from 'tslib';
import { Employee } from '../../interfaces/employee';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-details',
  imports: [CommonModule],
  templateUrl: './employee-details.component.html',
  styleUrl: './employee-details.component.css'
})
export class EmployeeDetailsComponent {
  @Input() employee!: Employee;
}
