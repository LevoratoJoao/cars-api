import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PaginationComponent } from '../pagination/pagination.component';
import { Sale } from '../../interfaces/sale';
import { SaleService } from '../../services/sale/sale.service';
import { SaleDetailsComponent } from '../sale-details/sale-details.component';

@Component({
  selector: 'app-sale',
  imports: [CommonModule, FormsModule, PaginationComponent, SaleDetailsComponent],
  templateUrl: './sale.component.html',
  styleUrl: './sale.component.css'
})
export class SaleComponent {
  private _saleList: Sale[] = [];

  date: string = ""; //TODO
  carName: string = "";
  customerName: string = "";
  employeeName: string = "";
  minPrice: number = 0;
  maxPrice: number = 99999999;

  paginatedData: any[] = [];
  currentPage: number = 1;
  sizeOfPage: number = 5;
  totalItems: number = 0;

  constructor(private saleService: SaleService) { }

  ngOnInit(): void {
    this.saleService.getAllSales(this.currentPage, this.sizeOfPage).subscribe(
      (saleList: Sale[]) => {
        this._saleList = saleList;
        console.log(saleList);
      }
    );

  }

  fetchData(): void {
    this.saleService.getFilteredSales(this.currentPage, this.sizeOfPage, this.date, this.minPrice, this.maxPrice, this.carName, this.customerName, this.employeeName).subscribe(
      (saleList: Sale[]) => {
        this._saleList = saleList;
      }
    );
  }

  applyFilter(): void {
    this.minPrice = this.minPrice === null ? 0 : this.minPrice;
    this.maxPrice = this.maxPrice === null ? 9999999 : this.maxPrice;
    this.fetchData();
    console.log(this.saleList);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.saleService.getAllSales(this.currentPage, this.sizeOfPage).subscribe(
      (saleList: Sale[]) => {
        this._saleList = saleList;
      }
    );
  }

  public get saleList(): Sale[] {
    return this._saleList;
  }
  public set saleList(value: Sale[]) {
    this._saleList = value;
  }
}
