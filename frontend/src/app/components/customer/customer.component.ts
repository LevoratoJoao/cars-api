import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PaginationComponent } from '../pagination/pagination.component';
import { Customer } from '../../interfaces/customer';
import { CustomerService } from '../../services/cutomer/customer.service';
import { CustomerDetailsComponent } from '../customer-details/customer-details.component';

@Component({
  selector: 'app-customer',
  imports: [CommonModule, FormsModule, PaginationComponent, CustomerDetailsComponent],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerComponent {
  private _customerList: Customer[] = [];

  firstName: string = "";
  lastName: string = "";
  email: string = "";
  phoneNumber: string = "";

  paginatedData: any[] = [];
  currentPage: number = 1;
  sizeOfPage: number = 5;
  totalItems: number = 0;

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.customerService.getAllCustomers(this.currentPage, this.sizeOfPage).subscribe(
      (customerList: Customer[]) => {
        this.customerList = customerList;
      }
    );
  }

  fetchData(): void {
    this.customerService.getFilteredCustomers(this.currentPage, this.sizeOfPage, this.firstName, this.lastName, this.phoneNumber, this.email).subscribe(
      (customerList: Customer[]) => {
        this.customerList = customerList;
      }
    )
  }

  applyFilter(): void {
    this.fetchData();
    console.log(this.customerList);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.customerService.getAllCustomers(this.currentPage, this.sizeOfPage).subscribe(
      (employeeList: Customer[]) => {
        this.customerList = employeeList;
      }
    );
  }

  public get customerList(): Customer[] {
    return this._customerList;
  }
  public set customerList(value: Customer[]) {
    this._customerList = value;
  }
}
