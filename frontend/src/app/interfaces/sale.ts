import { Car } from "./car";
import { Customer } from "./customer";
import { Employee } from "./employee";

export interface Sale {
    id: number;
    date: string;
    sale_price: number;
    customer: Customer;
    car: Car;
    employee: Employee;
}
