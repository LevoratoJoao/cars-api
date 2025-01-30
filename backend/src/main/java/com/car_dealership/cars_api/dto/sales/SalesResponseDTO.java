package com.car_dealership.cars_api.dto.sales;

import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.models.Customer;
import com.car_dealership.cars_api.models.Employee;

import java.time.LocalDate;

public record SalesResponseDTO(Integer id, LocalDate date, Float sale_price, Car car, Customer customer, Employee employee) {
}
