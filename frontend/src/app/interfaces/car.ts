import { Manufacturer } from "./manufacturer";

export interface Car {
    car_id: number,
    car_name: string,
    model: string,
    release_year: number,
    motor: string,
    kilometers: number,
    price: number,
    manufacturer: Manufacturer,
    colors: string[]
}