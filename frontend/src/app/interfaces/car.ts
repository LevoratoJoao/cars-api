import { Manufacturer } from "./manufacturer";

export interface Car {
    id: number,
    name: string,
    model: string,
    release_year: number,
    motor: string,
    kilometers: number,
    price: number,
    manufacturer: Manufacturer,
    color: string[]
}