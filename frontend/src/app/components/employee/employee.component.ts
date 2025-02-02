import { Component } from '@angular/core';
import { Employee } from '../../interfaces/employee';
import { EmployeeService } from '../../services/employee/employee.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaginationComponent } from '../pagination/pagination.component';
import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';

@Component({
  selector: 'app-employee',
  imports: [CommonModule, FormsModule, PaginationComponent, EmployeeDetailsComponent],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent {
  private _employeeList: Employee[] = [];

  firstName: string = "";
  lastName: string = "";
  position: string = "";
  email: string = "";
  phoneNumber: string = "";
  minSalary: number = 0;
  maxSalary: number = 99999999;
  carName: string = "";

  paginatedData: any[] = [];
  currentPage: number = 1;
  sizeOfPage: number = 3;
  totalItems: number = 0;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getAllEmployees(this.currentPage, this.sizeOfPage).subscribe(
      (employeeList: Employee[]) => {
        this._employeeList = employeeList;
      }
    );
  }

  fetchData(): void {
    this.employeeService.getFilteredEmployees(this.currentPage, this.sizeOfPage, this.firstName, this.lastName, this.position, this.email, this.phoneNumber, this.minSalary, this.maxSalary).subscribe(
      (employeeList: Employee[]) => {
        this.employeeList = employeeList;
      }
    )
  }

  applyFilter(): void {
    this.minSalary = this.minSalary === null ? 0 : this.minSalary;
    this.maxSalary = this.maxSalary === null ? 9999999 : this.maxSalary;
    this.fetchData();
    console.log(this.employeeList);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.employeeService.getAllEmployees(this.currentPage, this.sizeOfPage).subscribe(
      (employeeList: Employee[]) => {
        this._employeeList = employeeList;
      }
    );
  }

  public get employeeList(): Employee[] {
    return this._employeeList;
  }
  public set employeeList(value: Employee[]) {
    this._employeeList = value;
  }

}
