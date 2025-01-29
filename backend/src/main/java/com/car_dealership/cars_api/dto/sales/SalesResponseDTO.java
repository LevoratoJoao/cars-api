package com.car_dealership.cars_api.dto.sales;

import com.car_dealership.cars_api.dto.car.CarResponseDTO;
import com.car_dealership.cars_api.dto.customer.CustomerResponseDTO;
import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.customer.Customer;
import com.car_dealership.cars_api.models.employee.Employee;

import java.time.LocalDate;

public record SalesResponseDTO(Integer id, LocalDate date, Float sale_price, Car car, Customer customer, Employee employee) {
}
