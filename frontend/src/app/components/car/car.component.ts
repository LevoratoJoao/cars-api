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

@Component({
  selector: 'app-car',
  imports: [CommonModule, FormsModule, CarDetailsComponent],
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

  constructor(private carService: CarService, private manuService: ManufacturerService, private colorService: ColorService) { }

  ngOnInit(): void {
    this.carService.getAllCars().subscribe(
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
  }

  applyFilter() {
    this.minPrice = this.minPrice === null ? 0 : this.minPrice;
    this.maxPrice = this.maxPrice === null ? 9999999 : this.maxPrice;
    this.carService.getFilteredCars(this.selectedManu, this.selectedColor, this.selectedModel, this.minPrice, this.maxPrice, this.carName).subscribe(
      (filteredList: Car[]) => {
        this.carsList = filteredList;
      }
    );
    console.log(this._carsList);
  }

  public get carsList(): Car[] {
    return this._carsList;
  }

  public set carsList(value: Car[]) {
    this._carsList = value;
  }
}
