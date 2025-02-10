import { Manufacturer } from "./manufacturer";

export interface Car {
    car_id: number,
    car_name: string,
    model: string,
    release_year: number,
    motor: string,
    kilometers: number,
    price: number,
    sold: boolean,
    manufacturer: Manufacturer,
    colors: string[]
}