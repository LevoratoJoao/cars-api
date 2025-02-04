import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Car } from '../../interfaces/car';
import { CarService } from '../../services/car/car.service';
import { Color } from '../../interfaces/color';
import { Manufacturer } from '../../interfaces/manufacturer';
import { ColorService } from '../../services/color/color.service';
import { ManufacturerService } from '../../services/manufacturer/manufacturer.service';
import { FormsModule } from '@angular/forms';
import { CarDetailsComponent } from "../car-details/car-details.component";
import { PaginationComponent } from "../pagination/pagination.component";

@Component({
  selector: 'app-car',
  imports: [CommonModule, FormsModule, CarDetailsComponent, PaginationComponent],
  templateUrl: './car.component.html',
  styleUrl: './car.component.css'
})
export class CarComponent {
  private _carsList: Car[] = [];

  manuList: Manufacturer[] = [];
  colorList: Color[] = [];

  selectedManu: string = "";
  selectedColor: string = "";
  selectedModel: string = "";
  minPrice: number = 0;
  maxPrice: number = 99999999;
  carName: string = "";

  paginatedData: any[] = [];
  currentPage: number = 1;
  sizeOfPage: number = 5;
  totalItems: number = 0;

  constructor(private carService: CarService, private manuService: ManufacturerService, private colorService: ColorService) { }

  ngOnInit(): void {
    this.carService.getAllCars(this.currentPage, this.sizeOfPage).subscribe(
      (carsList: Car[]) => {
        this._carsList = carsList;
      }
    );
    this.manuService.getAllManufacturers().subscribe(
      (manuList: Manufacturer[]) => {
        this.manuList = manuList;
      }
    );
    this.colorService.getAllColors().subscribe(
      (colorList: Color[]) => {
        this.colorList = colorList;
      }
    );
    this.carService.getLength().subscribe(
      (length: number) => {
        this.totalItems = length;
      }
    );
  }

  fetchData(): void {
    this.carService.getFilteredCars(this.currentPage, this.sizeOfPage, this.selectedManu, this.selectedColor, this.selectedModel, this.minPrice, this.maxPrice, this.carName).subscribe(
      (filteredList: Car[]) => {
        this.carsList = filteredList;
      }
    );
    console.log(this.carsList);
  }

  applyFilter(): void {
    this.minPrice = this.minPrice === null ? 0 : this.minPrice;
    this.maxPrice = this.maxPrice === null ? 9999999 : this.maxPrice;
    this.fetchData();
    console.log(this._carsList);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.fetchData();
  }

  // goToPreviousPage(): void {
  //   if (this.currentPage > 0) {
  //     this.currentPage--;
  //     this.fetchData();
  //   }
  // }

  // goToNextPage(): void {
  //   if (this.currentPage < this.totalPages) {
  //     this.currentPage++;
  //     this.fetchData();
  //   }
  // }

  // goToPage(event: Event): void {
  //   const page = event.target as HTMLInputElement;
  //   const pageNumber = parseInt(page.value, 10);
  //   if (pageNumber && pageNumber >= 1 && pageNumber <= this.totalPages && pageNumber !== this.currentPage) {
  //     this.currentPage = pageNumber;
  //     this.fetchData();
  //   }
  // }

  // get pageNumbers(): number[] {
  //   return Array.from({ length: this.totalPages }, (_, index) => index + 1);
  // }

  // get totalPages(): number {
  //   return Math.ceil(this.totalItems / this.sizeOfPage);
  // }

  public get carsList(): Car[] {
    return this._carsList;
  }

  public set carsList(value: Car[]) {
    this._carsList = value;
  }
}
